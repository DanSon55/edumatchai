class LoadingSpinner {
    constructor() {
        this.element = document.createElement('div');
        this.element.className = 'loading-spinner';
        this.element.innerHTML = `
            <div class="spinner-overlay">
                <div class="spinner-container">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Загрузка...</span>
                    </div>
                    <div class="spinner-text mt-2">Подбираем университеты...</div>
                </div>
            </div>
        `;
        
        // Добавляем стили
        const style = document.createElement('style');
        style.textContent = `
            .spinner-overlay {
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background: rgba(255, 255, 255, 0.8);
                display: flex;
                justify-content: center;
                align-items: center;
                z-index: 9999;
                backdrop-filter: blur(3px);
            }
            
            .spinner-container {
                text-align: center;
                padding: 2rem;
                background: white;
                border-radius: 8px;
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }
            
            .spinner-text {
                color: #666;
                font-size: 1.1rem;
            }
            
            .spinner-border {
                width: 3rem;
                height: 3rem;
            }
        `;
        document.head.appendChild(style);
    }

    show() {
        document.body.appendChild(this.element);
    }

    hide() {
        if (this.element.parentNode) {
            this.element.parentNode.removeChild(this.element);
        }
    }
}

// Экспортируем глобально
window.loadingSpinner = new LoadingSpinner();