class RecommendationsDisplay {
    constructor(recommendations) {
        this.recommendations = recommendations;
        this.createContainer();
    }

    createContainer() {
        const container = document.createElement('div');
        container.className = 'recommendations-container card mb-4';
        container.innerHTML = `
            <div class="card-body">
                <h5 class="card-title">
                    <i class="bi bi-lightbulb"></i> 
                    Персональные рекомендации
                </h5>
                <div id="recommendationsList" class="row"></div>
            </div>
        `;

        const resultsDiv = document.getElementById('results');
        resultsDiv.insertBefore(container, document.getElementById('universitiesList'));

        this.displayRecommendations();
    }

    displayRecommendations() {
        const list = document.getElementById('recommendationsList');
        
        Object.entries(this.recommendations)
            .sort((a, b) => b[1].score - a[1].score)
            .forEach(([university, match]) => {
                const card = document.createElement('div');
                card.className = 'col-md-6 mb-3';
                card.innerHTML = `
                    <div class="recommendation-card h-100">
                        <div class="recommendation-header">
                            <h6 class="mb-0">${university.name}</h6>
                            <span class="match-score ${this.getScoreClass(match.score)}">
                                ${Math.round(match.score * 100)}% совпадение
                            </span>
                        </div>
                        <div class="recommendation-body">
                            <ul class="match-reasons">
                                ${match.reasons.map(reason => 
                                    `<li><i class="bi bi-check-circle-fill text-success"></i> ${reason}</li>`
                                ).join('')}
                            </ul>
                        </div>
                        <div class="recommendation-footer">
                            <button class="btn btn-outline-primary btn-sm learn-more-btn" 
                                    data-university-id="${university.id}">
                                Подробнее
                            </button>
                        </div>
                    </div>
                `;

                // Добавляем обработчик для кнопки "Подробнее"
                const learnMoreBtn = card.querySelector('.learn-more-btn');
                learnMoreBtn.addEventListener('click', () => {
                    const targetCard = document.querySelector(
                        `.university-card[data-uni-id="${university.id}"]`
                    );
                    if (targetCard) {
                        targetCard.scrollIntoView({ behavior: 'smooth', block: 'center' });
                        targetCard.classList.add('highlight-card');
                        setTimeout(() => {
                            targetCard.classList.remove('highlight-card');
                        }, 2000);
                    }
                });

                list.appendChild(card);
            });
    }

    getScoreClass(score) {
        if (score >= 0.8) return 'score-high';
        if (score >= 0.6) return 'score-medium';
        return 'score-low';
    }

    update(newRecommendations) {
        this.recommendations = newRecommendations;
        const list = document.getElementById('recommendationsList');
        list.innerHTML = '';
        this.displayRecommendations();
    }
}

// Добавляем стили для рекомендаций
const style = document.createElement('style');
style.textContent = `
    .recommendation-card {
        border: 1px solid #e9ecef;
        border-radius: 8px;
        padding: 1rem;
        background: white;
        transition: all 0.3s ease;
    }

    .recommendation-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
    }

    .recommendation-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1rem;
        padding-bottom: 0.5rem;
        border-bottom: 1px solid #e9ecef;
    }

    .match-score {
        padding: 0.25rem 0.5rem;
        border-radius: 4px;
        font-size: 0.8rem;
        font-weight: 600;
    }

    .score-high {
        background-color: #d4edda;
        color: #155724;
    }

    .score-medium {
        background-color: #fff3cd;
        color: #856404;
    }

    .score-low {
        background-color: #f8d7da;
        color: #721c24;
    }

    .match-reasons {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .match-reasons li {
        margin-bottom: 0.5rem;
        display: flex;
        align-items: start;
        gap: 0.5rem;
        font-size: 0.9rem;
    }

    .match-reasons li:last-child {
        margin-bottom: 0;
    }

    .recommendation-footer {
        margin-top: 1rem;
        text-align: right;
    }

    .highlight-card {
        animation: highlight-pulse 2s ease-in-out;
    }

    @keyframes highlight-pulse {
        0%, 100% {
            box-shadow: none;
        }
        50% {
            box-shadow: 0 0 20px rgba(13, 110, 253, 0.4);
        }
    }
`;
document.head.appendChild(style);

// Экспортируем для использования в других модулях
window.RecommendationsDisplay = RecommendationsDisplay;