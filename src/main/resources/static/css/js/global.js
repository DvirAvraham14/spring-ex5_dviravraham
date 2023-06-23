const reloadBudget = (date) => {
    const _csrf = document.querySelector("meta[name='_csrf']").content;
    fetch('/budget/getBudget', {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': _csrf,
        },
        body: JSON.stringify({date: date})
    }).then((response) => {
        if (response.ok) {
            return response.text();
        }
    }).then((html) => {
        if (html !== "") {
            const currentDocument = document.getElementById("budgetContainer");
            currentDocument.innerHTML = html;
        }
    })
        .catch((error) => {
                console.log(error);
            }
        );
}
