class ResultSharing {
    static generateShareableUrl(searchId) {
        const baseUrl = window.location.origin;
        return `${baseUrl}?search=${searchId}`;
    }

    static async shareResults() {
        if (!currentResults) {
            showNotification('Нет результатов для sharing', true);
            return;
        }

        try {
            const url = this.generateShareableUrl(currentResults.searchId);
            
            if (navigator.share) {
                await navigator.share({
                    title: 'Подобранные университеты',
                    text: `Посмотрите подборку университетов на ЭдуМатч`,
                    url: url
                });
            } else {
                await navigator.clipboard.writeText(url);
                showNotification('Ссылка скопирована в буфер обмена');
            }
        } catch (error) {
            console.error('Ошибка при шаринге:', error);
            showNotification('Не удалось поделиться результатами', true);
        }
    }

    static async loadSharedResults(searchId) {
        try {
            const response = await fetch(`/history/${searchId}`);
            if (!response.ok) {
                throw new Error('Поиск не найден');
            }

            const record = await response.json();
            currentResults = {
                universities: record.matches,
                totalMatches: record.matches.length,
                searchId: searchId
            };

            displayResults(currentResults);
            showNotification('Загружены shared результаты');
        } catch (error) {
            console.error('Ошибка при загрузке shared результатов:', error);
            showNotification('Не удалось загрузить shared результаты', true);
        }
    }
}

// Добавляем обработчик для кнопки шаринга
document.getElementById('shareResults')?.addEventListener('click', () => {
    ResultSharing.shareResults();
});

// Проверяем URL при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const searchId = urlParams.get('search');
    if (searchId) {
        ResultSharing.loadSharedResults(searchId);
    }
});