

async function reportAppPasswordCredentials() {

    const currentAcc = myMSALObj.getAccountByHomeId(accountId);
    if (currentAcc) {
        await getTokenPopup(loginRequest, currentAcc)
            .then(response => {
                fetch('/reports/applications/passwordCredentials', {
                    method: 'GET',
                    headers: {  
                        'Authorization': 'Bearer ' + response.accessToken
                    }
                })
                .then(response => response.text())
                .then(data => {
                    $('#result').html(data);
                    $('#result').show();
                })
                .catch(error => {
                    console.log(error);
                });
            }).catch(error => {
                console.log(error);
            });
    }
}