class UniversitySorter {
    constructor(results) {
        this.results = results;
        this.addSortControls();
    }

    addSortControls() {
        const controls = document.createElement('div');
        controls.className = 'card mb-3';
        controls.innerHTML = `
            <div class="card-body">
                <div class="d-flex align-items-center">
                    <label class="me-3">Сортировать по:</label>
                    <select class="form-select w-auto" id="sortSelect">
                        <option value="ranking">Рейтингу (лучшие первые)</option>
                        <option value="admissionChance">Шансу поступления (выше)</option>
                        <option value="scholarshipChance">Шансу стипендии (выше)</option>
                        <option value="costAsc">Стоимости (дешевле)</option>
                        <option value="costDesc">Стоимости (дороже)</option>
                    </select>
                </div>
            </div>
        `;

        const resultsDiv = document.getElementById('results');
        resultsDiv.insertBefore(controls, document.getElementById('universitiesList'));

        document.getElementById('sortSelect').addEventListener('change', (e) => {
            this.sortUniversities(e.target.value);
        });
    }

    sortUniversities(criterion) {
        const sorted = [...this.results.universities].sort((a, b) => {
            switch (criterion) {
                case 'ranking':
                    return a.ranking - b.ranking;
                case 'admissionChance':
                    return b.admissionChance - a.admissionChance;
                case 'scholarshipChance':
                    return b.scholarshipChance - a.scholarshipChance;
                case 'costAsc':
                    return a.annualCost - b.annualCost;
                case 'costDesc':
                    return b.annualCost - a.annualCost;
                default:
                    return 0;
            }
        });

        displayResults({
            universities: sorted,
            totalMatches: sorted.length
        });
    }
}

// Экспортируем для использования в других модулях
window.UniversitySorter = UniversitySorter;