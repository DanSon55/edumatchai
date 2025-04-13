function generatePDF(universities, isComparison = false) {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();
    
    // Настройка шрифта для поддержки кириллицы
    doc.addFont('https://cdn.jsdelivr.net/npm/@fontsource/roboto@4.5.8/files/roboto-all-400-normal.woff', 'Roboto', 'normal');
    doc.setFont('Roboto');

    // Заголовок
    doc.setFontSize(16);
    doc.text(isComparison ? 'Сравнение университетов' : 'Результаты подбора университетов', 20, 20);
    
    let yPos = 40;
    
    universities.forEach((uni, index) => {
        if (yPos > 250) {
            doc.addPage();
            yPos = 20;
        }

        doc.setFontSize(14);
        doc.text(uni.name, 20, yPos);
        yPos += 10;

        doc.setFontSize(10);
        const details = [
            `Страна: ${uni.country}`,
            `Рейтинг: ${uni.ranking}`,
            `Стоимость: $${uni.annualCost.toLocaleString('ru-RU')}/год`,
            `Шанс поступления: ${Math.round(uni.admissionChance * 100)}%`,
            `Шанс стипендии: ${Math.round(uni.scholarshipChance * 100)}%`,
            `Работа после выпуска: ${uni.workVisaPolicy}`
        ];

        details.forEach(line => {
            doc.text(line, 30, yPos);
            yPos += 7;
        });

        yPos += 10;
    });

    // Добавляем дату генерации
    const date = new Date().toLocaleDateString('ru-RU');
    doc.setFontSize(8);
    doc.text(`Отчёт сгенерирован: ${date}`, 20, 280);

    // Сохраняем файл
    const filename = isComparison ? 'universities-comparison.pdf' : 'universities-matches.pdf';
    doc.save(filename);
}

// Экспорт обычного отчета
window.exportToPDF = function(universities) {
    generatePDF(universities, false);
};

// Экспорт сравнения
window.comparePDF = function(universities) {
    generatePDF(universities, true);
};