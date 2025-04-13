class UniversityTooltip {
    constructor() {
        this.tooltip = document.createElement('div');
        this.tooltip.className = 'university-tooltip';
        document.body.appendChild(this.tooltip);
        this.addStyles();
    }

    addStyles() {
        const style = document.createElement('style');
        style.textContent = `
            .university-tooltip {
                position: fixed;
                display: none;
                background: white;
                border-radius: 8px;
                box-shadow: 0 4px 20px rgba(0,0,0,0.15);
                padding: 1rem;
                max-width: 400px;
                z-index: 1000;
                font-size: 0.9rem;
                animation: tooltip-fade 0.2s ease-in-out;
            }

            @keyframes tooltip-fade {
                from {
                    opacity: 0;
                    transform: translateY(10px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .tooltip-section {
                margin-bottom: 0.8rem;
            }

            .tooltip-section:last-child {
                margin-bottom: 0;
            }

            .tooltip-title {
                font-weight: bold;
                margin-bottom: 0.3rem;
            }

            .tooltip-grid {
                display: grid;
                grid-template-columns: repeat(2, 1fr);
                gap: 0.5rem;
            }

            .tooltip-stat {
                display: flex;
                align-items: center;
                gap: 0.5rem;
            }

            .tooltip-tag {
                background: #f0f0f0;
                padding: 0.2rem 0.5rem;
                border-radius: 4px;
                font-size: 0.8rem;
                margin-right: 0.3rem;
                margin-bottom: 0.3rem;
                display: inline-block;
            }
        `;
        document.head.appendChild(style);
    }

    show(event, metadata) {
        this.tooltip.innerHTML = this.generateContent(metadata);
        this.tooltip.style.display = 'block';
        
        const rect = event.target.getBoundingClientRect();
        const tooltipRect = this.tooltip.getBoundingClientRect();
        
        // Позиционирование тултипа
        let left = event.clientX + 10;
        let top = event.clientY + 10;

        // Проверка выхода за границы экрана
        if (left + tooltipRect.width > window.innerWidth) {
            left = window.innerWidth - tooltipRect.width - 10;
        }
        if (top + tooltipRect.height > window.innerHeight) {
            top = window.innerHeight - tooltipRect.height - 10;
        }

        this.tooltip.style.left = left + 'px';
        this.tooltip.style.top = top + 'px';
    }

    hide() {
        this.tooltip.style.display = 'none';
    }

    generateContent(metadata) {
        return `
            <div class="tooltip-section">
                <div class="tooltip-title">Общая информация</div>
                <div class="tooltip-grid">
                    <div class="tooltip-stat">👥 Студентов: ${metadata.studentCount.toLocaleString('ru-RU')}</div>
                    <div class="tooltip-stat">🌍 Иностранных: ${metadata.internationalStudentPercentage}%</div>
                    <div class="tooltip-stat">📊 Принимают: ${metadata.acceptanceRate}%</div>
                    <div class="tooltip-stat">📚 Средний GPA: ${metadata.averageGPA}</div>
                </div>
            </div>

            <div class="tooltip-section">
                <div class="tooltip-title">Расположение</div>
                <div>📍 ${metadata.location}</div>
                <div>🌤️ Климат: ${metadata.climate}</div>
            </div>

            <div class="tooltip-section">
                <div class="tooltip-title">Стоимость жизни</div>
                <div>🏠 Проживание: $${metadata.housingCost}/мес</div>
                <div>💰 Расходы: $${metadata.livingExpenses}/мес</div>
            </div>

            <div class="tooltip-section">
                <div class="tooltip-title">Сильные направления</div>
                <div>
                    ${metadata.majorStrengths.map(major => 
                        `<span class="tooltip-tag">${major}</span>`
                    ).join('')}
                </div>
            </div>

            <div class="tooltip-section">
                <div class="tooltip-title">Карьера выпускников</div>
                <div>💼 Трудоустройство: ${metadata.employmentRate}%</div>
                <div>💵 Начальная зарплата: $${metadata.averageStartingSalary.toLocaleString('ru-RU')}/год</div>
            </div>
        `;
    }

    init() {
        document.querySelectorAll('.university-card').forEach(card => {
            const metadata = JSON.parse(card.dataset.metadata || '{}');
            
            card.addEventListener('mouseenter', (e) => {
                this.show(e, metadata);
            });

            card.addEventListener('mouseleave', () => {
                this.hide();
            });
        });
    }
}

// Инициализация при загрузке страницы
document.addEventListener('DOMContentLoaded', () => {
    window.universityTooltip = new UniversityTooltip();
    window.universityTooltip.init();
});