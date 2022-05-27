const totalDOM = document.querySelector('#total');
const toDateDOM = document.querySelector('#toDate');
const fromDateDOM = document.querySelector('#fromDate');
const priceDOM = document.querySelector('#price');
const pricePerDay = priceDOM.value;
const btnSubmit = document.querySelector('button');

const ONE_DAY = 1000 * 60 * 60 * 24;

toDateDOM.addEventListener('change', event => {

    const fromDate = fromDateDOM.value;
    const toDate = toDateDOM.value;

    const fDate = new Date(fromDate);
    const tDate = new Date(toDate);

    const distanceDay = (tDate.getTime() - fDate.getTime()) / ONE_DAY;

    if (distanceDay <= 0) {
        btnSubmit.disabled = true;
        return;
    }
    btnSubmit.disabled = false;

    const total = distanceDay * pricePerDay;

    totalDOM.textContent = total;
    priceDOM.value = total;

})
