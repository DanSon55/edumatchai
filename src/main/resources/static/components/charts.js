class ChanceVisualizer {
    static createChanceIndicator(chance, type = 'admission') {
        const indicator = document.createElement('div');
        indicator.className = 'chance-indicator';
        
        const percentage = Math.round(chance * 100);
        const label = type === 'admission' ? 'поступления' : 'стипендии';
        
        const bar = document.createElement('div');
        bar.className = 'chance-bar';
        
        const fill = document.createElement('div');
        fill.className = `chance-bar-fill ${this.getChanceClass(chance)}`;
        fill.style.width = '0%';
        bar.appendChild(fill);

        indicator.innerHTML = `
            <span class="chance-label">${label}: ${percentage}%</span>
        `;
        indicator.appendChild(bar);

        // Анимация заполнения
        setTimeout(() => {
            fill.style.width = `${percentage}%`;
        }, 100);

        return indicator;
    }

    static getChanceClass(chance) {
        if (chance >= 0.7) return 'high';
        if (chance >= 0.4) return 'medium';
        return 'low';
    }

    static initializeCharts() {
        document.querySelectorAll('.university-card').forEach(card => {
            const admissionChance = parseFloat(card.dataset.admissionChance);
            const scholarshipChance = parseFloat(card.dataset.scholarshipChance);
            
            const statsContainer = card.querySelector('.university-stats');
            if (statsContainer) {
                statsContainer.appendChild(
                    this.createChanceIndicator(admissionChance, 'admission')
                );
                statsContainer.appendChild(
                    this.createChanceIndicator(scholarshipChance, 'scholarship')
                );
            }
        });
    }
}

// Экспортируем для использования в других модулях
window.ChanceVisualizer = ChanceVisualizer;