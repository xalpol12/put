// Get the div element with id 'app'
const appDiv = document.getElementById('app');

// Create a new <p> element
const message = document.createElement('p');

// Set the text content of the <p> element
message.textContent = 'Hello, World!';

// Append the <p> element to the div
appDiv?.appendChild(message);