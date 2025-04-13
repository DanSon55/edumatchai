const ValidationRules = {
    gpa: {
        min: 0,
        max: 4.0,
        message: 'GPA должен быть от 0 до 4.0'
    },
    ielts: {
        min: 1.0,
        max: 9.0,
        message: 'IELTS должен быть от 1.0 до 9.0'
    },
    sat: {
        min: 400,
        max: 1600,
        message: 'SAT должен быть от 400 до 1600'
    },
    budget: {
        min: 1000,
        message: 'Минимальный бюджет должен быть не менее $1000'
    },
    familyIncome: {
        min: 0,
        message: 'Доход семьи не может быть отрицательным'
    }
};

function validateForm(formData) {
    const errors = [];
    
    // Проверка числовых полей
    Object.entries(ValidationRules).forEach(([field, rules]) => {
        const value = parseFloat(formData.get(field));
        if (isNaN(value)) {
            errors.push(`Поле ${field} должно быть числом`);
            return;
        }
        
        if (rules.min !== undefined && value < rules.min) {
            errors.push(rules.message);
        }
        if (rules.max !== undefined && value > rules.max) {
            errors.push(rules.message);
        }
    });

    // Проверка обязательных полей
    ['country', 'specialty', 'admissionYear', 'targetType'].forEach(field => {
        if (!formData.get(field)) {
            errors.push(`Поле ${field} обязательно для заполнения`);
        }
    });

    return errors;
}

// Добавляем живую валидацию при вводе
document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('matchForm');
    
    Object.entries(ValidationRules).forEach(([field, rules]) => {
        const input = form.querySelector(`[name="${field}"]`);
        if (!input) return;

        input.addEventListener('input', (e) => {
            const value = parseFloat(e.target.value);
            
            if (isNaN(value)) {
                input.setCustomValidity('Введите числовое значение');
                return;
            }

            if (rules.min !== undefined && value < rules.min) {
                input.setCustomValidity(rules.message);
            } else if (rules.max !== undefined && value > rules.max) {
                input.setCustomValidity(rules.message);
            } else {
                input.setCustomValidity('');
            }
        });
    });
});