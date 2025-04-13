class LanguageService {
    constructor() {
        this.currentLanguage = localStorage.getItem('language') || 'ru';
        this.translations = {
            ru: {
                formLabels: {
                    country: 'Страна',
                    specialty: 'Специальность',
                    gpa: 'Средний балл',
                    ielts: 'Балл IELTS',
                    sat: 'Балл SAT',
                    admissionYear: 'Год поступления',
                    targetType: 'Тип программы',
                    budget: 'Бюджет',
                    familyIncome: 'Доход семьи',
                    activities: 'Внеучебная деятельность',
                    campusLife: 'Кампус',
                    wantWorkAfter: 'Планирую работать после обучения'
                },
                buttons: {
                    submit: 'Найти университеты',
                    apply: 'Подать заявку',
                    compare: 'Сравнить',
                    export: 'Экспорт в PDF'
                },
                analytics: {
                    title: 'Аналитика',
                    trendsTitle: 'Тренды поступления',
                    costsTitle: 'Диапазон стоимости',
                    employmentTitle: 'Распределение по отраслям',
                    acceptanceRate: 'Процент поступления',
                    scholarshipRate: 'Доступность стипендий',
                    tuition: 'Обучение',
                    minCost: 'Минимальная стоимость',
                    maxCost: 'Максимальная стоимость',
                    employmentRate: 'Трудоустройство',
                    avgSalary: 'Средняя зарплата',
                    visaSuccess: 'Успех визы',
                    ranking: 'Рейтинг',
                    cost: 'Стоимость',
                    admissionChance: 'Шанс поступления',
                    scholarshipChance: 'Шанс стипендии'
                },
                notifications: {
                    success: 'Успешно',
                    error: 'Ошибка',
                    noResults: 'Подходящих университетов не найдено',
                    searchComplete: 'Поиск завершен'
                }
            },
            en: {
                formLabels: {
                    country: 'Country',
                    specialty: 'Specialty',
                    gpa: 'GPA',
                    ielts: 'IELTS Score',
                    sat: 'SAT Score',
                    admissionYear: 'Admission Year',
                    targetType: 'Program Type',
                    budget: 'Budget',
                    familyIncome: 'Family Income',
                    activities: 'Extracurricular Activities',
                    campusLife: 'Campus Life',
                    wantWorkAfter: 'Plan to work after studies'
                },
                buttons: {
                    submit: 'Find Universities',
                    apply: 'Apply',
                    compare: 'Compare',
                    export: 'Export to PDF'
                },
                analytics: {
                    title: 'Analytics',
                    trendsTitle: 'Admission Trends',
                    costsTitle: 'Cost Range',
                    employmentTitle: 'Industry Distribution',
                    acceptanceRate: 'Acceptance Rate',
                    scholarshipRate: 'Scholarship Availability',
                    tuition: 'Tuition',
                    minCost: 'Minimum Cost',
                    maxCost: 'Maximum Cost',
                    employmentRate: 'Employment Rate',
                    avgSalary: 'Average Salary',
                    visaSuccess: 'Visa Success',
                    ranking: 'Ranking',
                    cost: 'Cost',
                    admissionChance: 'Admission Chance',
                    scholarshipChance: 'Scholarship Chance'
                },
                notifications: {
                    success: 'Success',
                    error: 'Error',
                    noResults: 'No matching universities found',
                    searchComplete: 'Search completed'
                }
            }
        };
    }

    getText(key, section) {
        try {
            return this.translations[this.currentLanguage][section][key] || key;
        } catch (e) {
            console.error(`Translation not found for key: ${key} in section: ${section}`);
            return key;
        }
    }

    setLanguage(lang) {
        if (this.translations[lang]) {
            this.currentLanguage = lang;
            localStorage.setItem('language', lang);
            this.updatePageText();
            document.documentElement.lang = lang;
        }
    }

    updatePageText() {
        document.querySelectorAll('[data-i18n]').forEach(element => {
            const key = element.getAttribute('data-i18n');
            const section = element.getAttribute('data-i18n-section');
            if (key && section) {
                const translation = this.getText(key, section);
                if (element.tagName === 'INPUT' || element.tagName === 'TEXTAREA') {
                    element.placeholder = translation;
                } else {
                    element.textContent = translation;
                }
            }
        });
    }
}

// Initialize the language service
window.languageService = new LanguageService();