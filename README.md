Version Flyway Sql scripts
==========================

This project shows how flyway script versioning can be done effectively in a distributed development environment. If you are in a project that has multiple teams committing flyway scripts using the default way of using numbers, there is a high chance that multiple people used up the same version number, resulting in a conflict.

This problem can be averted by versioning the script with a timestamp upto millis. Conflicts can be significantly reduced.

Software Prerequisites
----------------------
1. JDK 6+
2. Maven 3+
3. Git

