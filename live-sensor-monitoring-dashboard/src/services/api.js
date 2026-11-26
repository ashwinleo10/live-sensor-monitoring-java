const API_BASE_URL = "http://localhost:8080";

async function authenticatedFetch(endpoint) {

    const token = localStorage.getItem("jwtToken");

    const response = await fetch(
        `${API_BASE_URL}${endpoint}`,
        {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        }
    );

    if (response.status === 401 || response.status === 403) {
        localStorage.removeItem("jwtToken");

        throw new Error("Session expired");
    }

    if (!response.ok) {
        throw new Error("Failed to fetch data");
    }

    return response.json();
}

export async function registerUser(email, password) {

    const response = await fetch(
        `${API_BASE_URL}/auth/register`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email,
                password,
            }),
        }
    );

    const data = await response.json();

    if (!response.ok) {
        throw new Error(
            data.message || "Registration failed"
        );
    }

    return data;
}

export async function loginUser(email, password) {

    const response = await fetch(
        `${API_BASE_URL}/auth/login`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                email,
                password,
            }),
        }
    );

    const data = await response.json();

    if (!response.ok) {
        throw new Error(
            data.message || "Login failed"
        );
    }

    return data;
}

export async function logoutUser() {

    const token = localStorage.getItem("jwtToken");

    if (!token) {
        return;
    }

    const response = await fetch(
        `${API_BASE_URL}/auth/logout`,
        {
            method: "POST",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json",
            },
        }
    );

    if (response.status === 401 || response.status === 403) {
        localStorage.removeItem("jwtToken");

        return;
    }

    if (!response.ok) {
        throw new Error("Logout failed");
    }

    localStorage.removeItem("jwtToken");
}

export async function getLatestReadings() {

    return authenticatedFetch(
        "/readings/latest"
    );
}

export async function getSensors() {

    return authenticatedFetch(
        "/sensors"
    );
}

export async function getAlerts() {

    return authenticatedFetch(
        "/alerts"
    );
}
