# Backend-oriented-task

To get list of currencies, run this command<br>
<code>curl http://localhost:8081/list/</code>

To qurery operation 1, run this command (replace {code} with 3 letter currency code (ISO 4217)<br>
{date} replace with date in format YYYY-MM-DD)<br>
<code>curl http://localhost:8081/exchange/{code}/{date}/</code>

To qurery operation 2, run this command (replace {code} with 3 letter currency code (ISO 4217)<br>
{count} replace with number of quotations)<br>
<code>curl http://localhost:8081/minmax/{code}/{count}/</code>

To qurery operation 3, run this command (replace {code} with 3 letter currency code (ISO 4217)<br>
{count} replace with number of quotations)<br>
<code>curl http://localhost:8081/diff/{code}/{count}/</code>
