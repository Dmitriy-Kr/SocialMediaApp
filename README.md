# Social Media API

<!DOCTYPE html>
<html>
<body>

<h1>Social Media API</h1>
<p>A RESTful API built with Spring Boot, Hibernate, and PostgreSQL for a simple social media application. This application provides functionality for creating and viewing posts, following users, fetching followers and following lists, liking posts, and fetching posts liked by users.</p>

<h2>Features</h2>
<ul>
    <li>Create and view posts</li>
    <li>Follow other users</li>
    <li>Fetch followers and following lists</li>
    <li>Like posts</li>
    <li>Fetch posts liked by users</li>
</ul>

<h2>Technologies Used</h2>
<ul>
    <li>Spring Boot</li>
    <li>Hibernate</li>
    <li>PostgreSQL</li>
    <li>JUnit and Mockito for testing</li>
</ul>

<h2>Prerequisites</h2>
<p>Ensure the following tools are installed on your system:</p>
<ul>
    <li>Java 17 or later</li>
    <li>PostgreSQL</li>
    <li>Maven</li>
    <li>An IDE (e.g., IntelliJ IDEA, Eclipse) or a text editor</li>
</ul>

<h2>Setup Instructions</h2>
<ol>
    <li><strong>Clone the repository:</strong><br>
        <code>git clone https://github.com/your-repo/social-media-api.git</code>
    </li>
    <li><strong>Navigate to the project directory:</strong><br>
        <code>cd social-media-api</code>
    </li>
    <li><strong>Configure the database:</strong>
        <ul>
            <li>Create a PostgreSQL database named <code>social_media_db</code>.</li>
            <li>Update the database connection details in <code>src/main/resources/application.properties</code>:
                <pre>
spring.datasource.url=jdbc:postgresql://localhost:5432/social_media_db
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
                </pre>
            </li>
        </ul>
    </li>
    <li><strong>Build the application:</strong><br>
        <code>mvn clean install</code>
    </li>
    <li><strong>Run the application:</strong><br>
        <code>mvn spring-boot:run</code>
    </li>
    <li><strong>Access the API:</strong>
        <ul>
            <li>The application will be available at <code>http://localhost:8080</code>.</li>
            <li>Use API tools like Postman or cURL to test endpoints.</li>
        </ul>
    </li>
</ol>

<h2>Testing</h2>
<p>Run the unit tests with:</p>
<pre><code>mvn test</code></pre>

<h2>Endpoints</h2>
<p>Below is a summary of the main API endpoints:</p>
<table>
    <thead>
        <tr>
            <th>Endpoint</th>
            <th>HTTP Method</th>
            <th>Description</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>/posts</td>
            <td>POST</td>
            <td>Create a new post</td>
        </tr>
        <tr>
            <td>/posts/user/{authorId}</td>
            <td>GET</td>
            <td>Fetch all posts by a specific user</td>
        </tr>
        <tr>
            <td>/posts/{postId}/like</td>
            <td>POST</td>
            <td>Like a specific post</td>
        </tr>
        <tr>
            <td>/users</td>
            <td>POST</td>
            <td>Create a new user</td>
        </tr>
        <tr>
            <td>/users/{id}/followers</td>
            <td>GET</td>
            <td>Fetch the followers of a specific user</td>
        </tr>
        <tr>
            <td>/users/{id}/following</td>
            <td>GET</td>
            <td>Fetch the following list of a specific user</td>
        </tr>
        <tr>
            <td>/users/{id}/liked-posts</td>
            <td>GET</td>
            <td>Fetch posts liked by a specific user</td>
        </tr>
    </tbody>
</table>

<h2>Feedback</h2>
<ol>
    <li><strong>Was it easy to complete the task using AI?</strong><br>
        It was a challenging task for me. 
        Maybe my prompts were weak (too general) because I wanted to generate a working application in a couple of steps.
    </li>
    <li><strong>How long did the task take you to complete?</strong><br>
        Approximately 3 hours.
    </li>
    <li><strong>Was the code ready to run after generation?</strong><br>
        Yes, overall the generated code was functional.
        Some adjustments were required to integrate the files into the project structure, especially in the unit test classes.
    </li>
    <li><strong>Which challenges did you face during completion of the task?</strong><br>
        Creating prompts that give the most appropriate result. Ensuring the generated code was compatible with existing project files.
    </li>
    <li><strong>Which specific prompts did you learn as a good practice to complete the task?</strong><br>
        - Asking for detailed instructions (e.g., "Create unit tests for ... ").<br>
        - Requesting structured output for project files like README, unit tests, or configuration.
    </li>
</ol>

<h2>License</h2>
<p>This project is licensed under the MIT License. See the <code>LICENSE</code> file for details.</p>

</body>
</html>
