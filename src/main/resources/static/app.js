document.addEventListener('DOMContentLoaded', () => {
    // Установка подсказок для полей
    const tooltips = {
        gpa: 'Средний балл по 4-балльной шкале. Например: 3.7',
        ielts: 'Балл IELTS (от 1 до 9). Например: 7.5',
        sat: 'Балл SAT (от 400 до 1600). Например: 1450',
        budget: 'Ваш годовой бюджет на обучение в USD',
        familyIncome: 'Годовой доход семьи в USD'
    };

    Object.entries(tooltips).forEach(([id, text]) => {
        const el = document.getElementById(id);
        if (el) el.title = text;
    });
});

// Функция для показа уведомлений
function showNotification(message, isError = false) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert ${isError ? 'alert-danger' : 'alert-success'} alert-dismissible fade show`;
    alertDiv.innerHTML = `
        ${window.languageService.getText(message, 'notifications')}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;
    document.querySelector('.container').insertAdjacentElement('afterbegin', alertDiv);
    
    // Автоматически скрываем через 5 секунд
    setTimeout(() => alertDiv.remove(), 5000);
}

let currentResults = null;

document.getElementById('matchForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const formData = new FormData(e.target);
    
    // Валидация формы
    const errors = validateForm(formData);
    if (errors.length > 0) {
        showNotification(errors.join('\n'), true);
        return;
    }

    // Показываем индикатор загрузки
    window.loadingSpinner.show();
    
    const submitBtn = e.target.querySelector('button[type="submit"]');
    submitBtn.disabled = true;

    try {
        const data = {
            country: formData.get('country'),
            specialty: formData.get('specialty'),
            gpa: parseFloat(formData.get('gpa')),
            ielts: parseFloat(formData.get('ielts')),
            sat: parseInt(formData.get('sat')),
            admissionYear: parseInt(formData.get('admissionYear')),
            targetType: formData.get('targetType'),
            budget: parseInt(formData.get('budget')),
            familyIncome: parseInt(formData.get('familyIncome')),
            activities: formData.get('activities'),
            campusLife: formData.get('campusLife'),
            wantWorkAfter: formData.get('wantWorkAfter') === 'on'
        };

        const response = await fetch('/match', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        const result = await response.json();

        if (!response.ok) {
            throw new Error(result.error || 'Произошла ошибка при поиске');
        }

        currentResults = result.results;
        displayResults(currentResults);
        
        if (result.results.universities.length > 0) {
            showNotification(`Найдено ${result.results.totalMatches} подходящих университетов!`);
        } else {
            showNotification('К сожалению, подходящих университетов не найдено', true);
        }
        
        // Обновляем историю поиска
        loadSearchHistory();
        
    } catch (error) {
        console.error('Ошибка:', error);
        showNotification('Произошла ошибка при поиске университетов. Попробуйте позже.', true);
    } finally {
        window.loadingSpinner.hide();
        submitBtn.disabled = false;
    }
});

// Добавляем обработчик для экспорта в PDF
document.getElementById('exportPDF').addEventListener('click', async () => {
    if (!currentResults || !currentResults.universities.length) {
        showNotification('Нет данных для экспорта', true);
        return;
    }

    try {
        window.loadingSpinner.show();
        await exportToPDF(currentResults.universities);
        showNotification('Отчёт успешно сохранен');
    } catch (error) {
        console.error('Ошибка при создании PDF:', error);
        showNotification('Ошибка при создании PDF', true);
    } finally {
        window.loadingSpinner.hide();
    }
});

async function displayResults(data) {
    const resultsDiv = document.getElementById('results');
    const universitiesList = document.getElementById('universitiesList');
    
    resultsDiv.classList.remove('d-none');
    universitiesList.innerHTML = '';

    try {
        // Получаем рекомендации
        const historyData = JSON.parse(localStorage.getItem('searchHistory') || '[]');
        const recommendationsResponse = await fetch('/recommendations', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                request: currentRequest,
                universities: data.universities,
                history: historyData
            })
        });

        if (recommendationsResponse.ok) {
            const recommendations = await recommendationsResponse.json();
            new RecommendationsDisplay(recommendations);
        }

        // Получаем аналитику
        const analyticsResponse = await fetch('/analytics', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ universities: data.universities })
        });
        
        if (analyticsResponse.ok) {
            const analytics = await analyticsResponse.json();
            new AnalyticsVisualizer(analytics).createCharts();
        }
    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
    }

    // Инициализируем сортировку и фильтры
    if (window.initializeFilters) {
        window.initializeFilters();
    }
    
    // Добавляем сортировку
    new UniversitySorter(data);

    if (data.universities.length === 0) {
        universitiesList.innerHTML = `
            <div class="alert alert-info">
                К сожалению, подходящих университетов не найдено. 
                Попробуйте изменить параметры поиска.
            </div>
        `;
        return;
    }

    data.universities.forEach((uni, index) => {
        const card = displayUniversityCard(uni, index);
        universitiesList.appendChild(card);
    });

    // Инициализируем сравнение университетов
    new UniversityComparison();

    // Плавная прокрутка к результатам
    resultsDiv.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

function displayUniversityCard(university, index) {
    const card = document.createElement('div');
    card.className = 'card mb-3 university-card';
    card.dataset.uniId = index;
    card.innerHTML = `
        <div class="card-body">
            <div class="row">
                <div class="col-md-8">
                    <h5 class="card-title">${university.name}</h5>
                    <div class="card-text">
                        <div class="mb-2">
                            <span class="badge bg-primary">🌍 ${university.country}</span>
                            <span class="badge bg-secondary">🏆 ${window.languageService.getText('ranking', 'analytics')}: ${university.ranking}</span>
                        </div>
                        <p>
                            💰 ${window.languageService.getText('cost', 'analytics')}: $${university.annualCost.toLocaleString()}/год<br>
                            📊 ${window.languageService.getText('admissionChance', 'analytics')}: 
                                <span class="text-${getChanceColor(university.admissionChance)}">
                                    ${Math.round(university.admissionChance * 100)}%
                                </span><br>
                            🎓 ${window.languageService.getText('scholarshipChance', 'analytics')}: 
                                <span class="text-${getChanceColor(university.scholarshipChance)}">
                                    ${Math.round(university.scholarshipChance * 100)}%
                                </span><br>
                            👩‍💼 ${university.workVisaPolicy}
                        </p>
                    </div>
                </div>
                <div class="col-md-4 text-end">
                    <a href="${university.applicationUrl}" target="_blank" 
                       class="btn btn-primary mb-2" data-i18n="apply" data-i18n-section="buttons">
                        ${window.languageService.getText('apply', 'buttons')}
                    </a>
                </div>
            </div>
        </div>
    `;
    return card;
}

function getChanceColor(chance) {
    if (chance >= 0.7) return 'success';
    if (chance >= 0.4) return 'warning';
    return 'danger';
}