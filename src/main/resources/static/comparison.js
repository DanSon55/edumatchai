class UniversityComparison {
    constructor() {
        this.selectedUniversities = new Set();
        this.maxComparisons = 3;
        this.addComparisonCheckboxes();
    }

    addComparisonCheckboxes() {
        document.querySelectorAll('.university-card').forEach(card => {
            const checkbox = document.createElement('div');
            checkbox.className = 'form-check comparison-checkbox';
            checkbox.innerHTML = `
                <input class="form-check-input" type="checkbox" value="${card.dataset.uniId}">
                <label class="form-check-label">Добавить к сравнению</label>
            `;
            
            checkbox.querySelector('input').addEventListener('change', (e) => {
                if (e.target.checked) {
                    if (this.selectedUniversities.size >= this.maxComparisons) {
                        e.target.checked = false;
                        showNotification('Можно сравнить максимум 3 университета', true);
                        return;
                    }
                    this.selectedUniversities.add(e.target.value);
                } else {
                    this.selectedUniversities.delete(e.target.value);
                }
                
                this.updateCompareButton();
            });
            
            card.appendChild(checkbox);
        });

        this.addCompareButton();
    }

    addCompareButton() {
        const button = document.createElement('button');
        button.id = 'compareButton';
        button.className = 'btn btn-primary position-fixed bottom-0 end-0 m-4 d-none';
        button.innerHTML = 'Сравнить выбранные';
        button.addEventListener('click', () => this.showComparison());
        document.body.appendChild(button);
    }

    updateCompareButton() {
        const button = document.getElementById('compareButton');
        if (this.selectedUniversities.size >= 2) {
            button.classList.remove('d-none');
            button.innerHTML = `Сравнить выбранные (${this.selectedUniversities.size})`;
        } else {
            button.classList.add('d-none');
        }
    }

    showComparison() {
        const universities = Array.from(this.selectedUniversities)
            .map(id => currentResults.universities.find(uni => uni.id === id))
            .filter(Boolean);

        const modal = document.createElement('div');
        modal.className = 'modal fade';
        modal.id = 'comparisonModal';
        modal.innerHTML = `
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Сравнение университетов</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead>
                                    <tr>
                                        <th>Параметр</th>
                                        ${universities.map(uni => `<th>${uni.name}</th>`).join('')}
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th>Страна</th>
                                        ${universities.map(uni => `<td>${uni.country}</td>`).join('')}
                                    </tr>
                                    <tr>
                                        <th>Рейтинг</th>
                                        ${universities.map(uni => `<td>${uni.ranking}</td>`).join('')}
                                    </tr>
                                    <tr>
                                        <th>Стоимость в год</th>
                                        ${universities.map(uni => `<td>$${uni.annualCost.toLocaleString('ru-RU')}</td>`).join('')}
                                    </tr>
                                    <tr>
                                        <th>Шанс поступления</th>
                                        ${universities.map(uni => `<td class="text-${getChanceColor(uni.admissionChance)}">${Math.round(uni.admissionChance * 100)}%</td>`).join('')}
                                    </tr>
                                    <tr>
                                        <th>Шанс стипендии</th>
                                        ${universities.map(uni => `<td class="text-${getChanceColor(uni.scholarshipChance)}">${Math.round(uni.scholarshipChance * 100)}%</td>`).join('')}
                                    </tr>
                                    <tr>
                                        <th>Работа после выпуска</th>
                                        ${universities.map(uni => `<td>${uni.workVisaPolicy}</td>`).join('')}
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                        <button type="button" class="btn btn-primary" onclick="window.comparePDF(${JSON.stringify(universities)})">
                            Сохранить сравнение в PDF
                        </button>
                    </div>
                </div>
            </div>
        `;

        document.body.appendChild(modal);
        const modalInstance = new bootstrap.Modal(modal);
        modalInstance.show();

        modal.addEventListener('hidden.bs.modal', () => {
            modal.remove();
        });
    }
}

// Экспортируем для использования в других модулях
window.UniversityComparison = UniversityComparison;