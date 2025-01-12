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
  