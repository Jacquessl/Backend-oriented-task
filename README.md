# Backend-oriented-task
<ul>
<li>To start the server, run this command<br>
  <code>java Main</code></li><br>
  
<li>To get list of currencies, run this command<br>
<code>curl http://localhost:8081/list</code></li><br>

<li>To qurery operation 1, run this command (replace {code} with 3 letter currency code (ISO 4217),<br>
{date} replace with date in format YYYY-MM-DD)<br>
<code>curl http://localhost:8081/exchange/{code}/{date}</code>

Example:<br>
<code>curl http://localhost:8081/exchange/usd/2023-04-21</code><br>
Output:<br> 
"Average exchange rate for US Dollar: 4.2006"</li>

<li>To qurery operation 2, run this command (replace {code} with 3 letter currency code (ISO 4217),<br>
{count} replace with number of quotations)<br>
<code>curl http://localhost:8081/minmax/{code}/{count}</code>

Example:<br>
<code>curl http://localhost:8081/minmax/usd/10</code><br>
Output:<br> 
"Maximum exchange rate for US Dollar in the last 10 days is: 4.2932<br>
Minimum exchange rate for US Dollar in the last 10 days is: 4.2006"</li>

<li>To qurery operation 3, run this command (replace {code} with 3 letter currency code (ISO 4217),<br>
{count} replace with number of quotations)<br>
<code>curl http://localhost:8081/diff/{code}/{count}</code><br>

Example:<br>
<code>curl http://localhost:8081/diff/usd/10</code><br>
Output:<br> 
"The biggest difference between ask and buy price for US Dollar in the last 10 days is: 0.08599999999999941"</li>
</ul>
