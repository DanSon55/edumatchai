class LanguageService {
    constructor() {
        this.currentLanguage = 'en';
        this.translations = {
            en: {
                'analytics.statistics': 'Statistics',
                'university.acceptance': 'Acceptance Rate',
                'university.cost': 'Average Cost',
                'university.employment': 'Employment Rate'
            },
            ru: {
                'analytics.statistics': 'Статистика',
                'university.acceptance': 'Процент поступления',
                'university.cost': 'Средняя стоимость',
                'university.employment': 'Уровень трудоустройства'
            }
        };
    }

    setLanguage(lang) {
        this.currentLanguage = lang;
        this.updateUI();
    }

    translate(key) {
        return this.translations[this.currentLanguage][key] || key;
    }

    updateUI() {
        document.querySelectorAll('[data-i18n]').forEach(element => {
            const key = element.getAttribute('data-i18n');
            element.textContent = this.translate(key);
        });
    }
}

window.languageService = new LanguageService();