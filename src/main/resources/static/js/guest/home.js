const formDOM = document.querySelector('form');
const selectsDOM = document.querySelectorAll('select');

selectsDOM.forEach(select => {
    select.addEventListener('change', event => {
        formDOM.submit();
    })
})