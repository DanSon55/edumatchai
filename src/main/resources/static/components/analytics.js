class AnalyticsVisualizer {
    constructor() {
        this.charts = {};
    }

    createCharts(data) {
        this.createAcceptanceRateChart(data);
        this.createCostDistributionChart(data);
        this.createEmploymentChart(data);
    }

    createAcceptanceRateChart(data) {
        const ctx = document.getElementById('acceptanceRateChart').getContext('2d');
        this.charts.acceptanceRate = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: data.map(u => u.name),
                datasets: [{
                    label: window.languageService.getText('acceptanceRate', 'analytics'),
                    data: data.map(u => u.acceptanceRate),
                    backgroundColor: 'rgba(54, 162, 235, 0.5)'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true,
                        max: 100
                    }
                }
            }
        });
    }

    createCostDistributionChart(data) {
        const ctx = document.getElementById('costDistributionChart').getContext('2d');
        this.charts.costDistribution = new Chart(ctx, {
            type: 'scatter',
            data: {
                datasets: [{
                    label: window.languageService.getText('cost', 'analytics'),
                    data: data.map(u => ({
                        x: u.ranking,
                        y: u.annualCost
                    })),
                    backgroundColor: 'rgba(255, 99, 132, 0.5)'
                }]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: window.languageService.getText('ranking', 'analytics')
                        }
                    },
                    y: {
                        title: {
                            display: true,
                            text: window.languageService.getText('tuition', 'analytics')
                        }
                    }
                }
            }
        });
    }

    createEmploymentChart(data) {
        const ctx = document.getElementById('employmentChart').getContext('2d');
        this.charts.employment = new Chart(ctx, {
            type: 'radar',
            data: {
                labels: [
                    window.languageService.getText('employmentRate', 'analytics'),
                    window.languageService.getText('avgSalary', 'analytics'),
                    window.languageService.getText('visaSuccess', 'analytics')
                ],
                datasets: data.map(u => ({
                    label: u.name,
                    data: [u.employmentRate, u.averageSalary/1000, u.visaSuccessRate],
                    fill: true,
                    backgroundColor: `rgba(${Math.random()*255}, ${Math.random()*255}, ${Math.random()*255}, 0.2)`
                }))
            },
            options: {
                responsive: true,
                scales: {
                    r: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

    updateCharts(data) {
        Object.values(this.charts).forEach(chart => chart.destroy());
        this.createCharts(data);
    }
}

window.analyticsVisualizer = new AnalyticsVisualizer();