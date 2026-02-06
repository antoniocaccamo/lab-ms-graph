

async function reportAppPasswordCredentials() {

    const currentAcc = myMSALObj.getAccountByHomeId(accountId);
    if (currentAcc) {
        await getTokenPopup(loginRequest, currentAcc)
            .then(response => {
                $('#result').html('');
                $('#spinner').show();
                fetch('/reports/applications/passwordCredentials', {
                    method: 'GET',
                    headers: {  
                        'Authorization': 'Bearer ' + response.accessToken
                    }
                })
                .then(response => response.text())
                .then(data => {
                    $('#spinner').hide();
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