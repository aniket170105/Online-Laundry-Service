
document.getElementById('login-form').addEventListener('submit', async function (e) {
    e.preventDefault();
    const userDTO = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
    };
    try {
        const response = await fetch('http://localhost:8081/auth/v1/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userDTO),
        });

        const {jwtToken, sessionToken} = await response.json();
        if (response.ok) {
            window.localStorage.setItem('jwtToken', jwtToken);
            window.localStorage.setItem('sessionToken', sessionToken);

            window.location.href = '../UserDashboard/userDashboard.html';
            // alert(`Success: ${message}`);
        } else {
            alert(`Error: Wrong Credential Login Again`);
        }
    } catch (error) {
        alert('An error occurred: ' + error.message);
    }
});