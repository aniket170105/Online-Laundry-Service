// Function to toggle the visibility of details in history items
document.querySelectorAll('.history-item').forEach((item) => {
    const detailsSection = item.querySelector('.details-section');
    detailsSection.style.display = 'none'; // Hide details by default

    item.addEventListener('click', () => {
        detailsSection.style.display =
            detailsSection.style.display === 'none' ? 'block' : 'none';
    });
});

// Interactive status tracker logic
document.querySelectorAll('.status-tracker .step').forEach((step, index, allSteps) => {
    step.addEventListener('click', (event) => {
        event.stopPropagation();
        allSteps.forEach((s, i) => {
            if (i <= index) {
                s.classList.add('completed');
            } else {
                s.classList.remove('completed');
            }
        });
    });
});

document.getElementById('search-btn').addEventListener('click', () => {
    const query = document.getElementById('search-bar').value.trim();
    const historyItems = document.querySelectorAll('.history-item');

    historyItems.forEach(item => {
        const id = item.getAttribute('data-id');
        if (query === '' || id.includes(query)) {
            item.style.display = 'block'; // Show matching items
        } else {
            item.style.display = 'none'; // Hide non-matching items
        }
    });
});

