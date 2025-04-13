class UniversityFilters {
    constructor(universities) {
        this.originalData = universities;
        this.filters = {
            maxCost: Infinity,
            minAdmissionChance: 0,
            minScholarshipChance: 0,
            rankingRange: [1, 200]
        };
    }

    setMaxCost(cost) {
        this.filters.maxCost = cost || Infinity;
        return this;
    }

    setMinAdmissionChance(chance) {
        this.filters.minAdmissionChance = chance || 0;
        return this;
    }

    setMinScholarshipChance(chance) {
        this.filters.minScholarshipChance = chance || 0;
        return this;
    }

    setRankingRange(min, max) {
        this.filters.rankingRange = [min || 1, max || 200];
        return this;
    }

    apply() {
        return this.originalData.filter(uni => {
            return uni.annualCost <= this.filters.maxCost &&
                   uni.admissionChance >= this.filters.minAdmissionChance &&
                   uni.scholarshipChance >= this.filters.minScholarshipChance &&
                   uni.ranking >= this.filters.rankingRange[0] &&
                   uni.ranking <= this.filters.rankingRange[1];
        });
    }
}

// Инициализация фильтров в интерфейсе
function initializeFilters() {
    const filterForm = document.createElement('div');
    filterForm.className = 'card mb-3';
    filterForm.innerHTML = `
        <div class="card-body">
            <h5 class="card-title">Фильтры</h5>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Максимальная стоимость (USD)</label>
                    <input type="number" id="maxCost" class="form-control" min="0" step="1000">
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Минимальный шанс поступления</label>
                    <select id="minAdmissionChance" class="form-select">
                        <option value="0">Любой</option>
                        <option value="0.3">30%</option>
                        <option value="0.5">50%</option>
                        <option value="0.7">70%</option>
                    </select>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label class="form-label">Минимальный шанс стипендии</label>
                    <select id="minScholarshipChance" class="form-select">
                        <option value="0">Любой</option>
                        <option value="0.3">30%</option>
                        <option value="0.5">50%</option>
                        <option value="0.7">70%</option>
                    </select>
                </div>
                <div class="col-md-6 mb-3">
                    <label class="form-label">Диапазон рейтинга</label>
                    <select id="rankingRange" class="form-select">
                        <option value="1,200">Все</option>
                        <option value="1,10">Топ 10</option>
                        <option value="1,50">Топ 50</option>
                        <option value="1,100">Топ 100</option>
                    </select>
                </div>
            </div>
            <button id="applyFilters" class="btn btn-primary">Применить фильтры</button>
        </div>
    `;

    const resultsDiv = document.getElementById('results');
    resultsDiv.insertBefore(filterForm, document.getElementById('universitiesList'));

    // Добавляем обработчик применения фильтров
    document.getElementById('applyFilters').addEventListener('click', () => {
        if (!currentResults) return;

        const filters = new UniversityFilters(currentResults.universities);
        
        const maxCost = parseFloat(document.getElementById('maxCost').value);
        if (!isNaN(maxCost)) filters.setMaxCost(maxCost);

        const admissionChance = parseFloat(document.getElementById('minAdmissionChance').value);
        filters.setMinAdmissionChance(admissionChance);

        const scholarshipChance = parseFloat(document.getElementById('minScholarshipChance').value);
        filters.setMinScholarshipChance(scholarshipChance);

        const [minRank, maxRank] = document.getElementById('rankingRange').value.split(',').map(Number);
        filters.setRankingRange(minRank, maxRank);

        const filteredUniversities = filters.apply();
        displayResults({
            universities: filteredUniversities,
            totalMatches: filteredUniversities.length
        });
    });
}

// Экспортируем для использования в других модулях
window.initializeFilters = initializeFilters;