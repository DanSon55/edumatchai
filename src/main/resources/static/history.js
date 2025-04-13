// Загрузка и отображение истории поиска
async function loadSearchHistory() {
    try {
        const response = await fetch('/history?limit=5');
        if (!response.ok) {
            throw new Error('Ошибка загрузки истории');
        }
        
        const history = await response.json();
        displayHistory(history);
    } catch (error) {
        console.error('Ошибка при загрузке истории:', error);
    }
}

function displayHistory(history) {
    const historyContainer = document.getElementById('searchHistory');
    historyContainer.innerHTML = '';

    if (history.length === 0) {
        historyContainer.innerHTML = '<div class="text-muted text-center p-3">История поиска пуста</div>';
        return;
    }

    history.forEach(record => {
        const item = document.createElement('div');
        item.className = 'list-group-item list-group-item-action';
        
        const date = new Date(record.timestamp).toLocaleDateString('ru-RU', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });

        item.innerHTML = `
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="mb-1">${record.request.country} - ${record.request.specialty}</h6>
                    <small class="text-muted">
                        GPA: ${record.request.gpa} | IELTS: ${record.request.ielts} | 
                        Бюджет: $${record.request.budget.toLocaleString('ru-RU')}
                    </small>
                </div>
                <div class="text-end">
                    <span class="badge bg-primary">${record.matches.length} вузов</span><br>
                    <small class="text-muted">${date}</small>
                </div>
            </div>
        `;

        // Добавляем обработчик для повторного просмотра результатов
        item.addEventListener('click', () => {
            displayResults({ universities: record.matches, totalMatches: record.matches.length });
            document.getElementById('results').scrollIntoView({ behavior: 'smooth' });
        });

        historyContainer.appendChild(item);
    });
}

// Загружаем историю при загрузке страницы
document.addEventListener('DOMContentLoaded', loadSearchHistory);

function loadSearchResults(record) {
    const results = {
        universities: record.matches,
        totalMatches: record.matches.length
    };
    displayResults(results);
    
    // Прокручиваем к результатам
    document.getElementById('results').scrollIntoView({ behavior: 'smooth' });
}