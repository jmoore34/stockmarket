# Sample queries

```sql
select Name, Assets from Person where Assets > 9000000;
MariaDB [StockMarket]>
```
```
+--------------------+------------+
| Name               | Assets     |
+--------------------+------------+
| Hilario Boxe       |    9631452 |
| Natka Fowley       |    9882876 |
| Isidor Cardon      |    9828579 |
| Kirbee Renak       |    9802448 |
| Rustin Findlow     |    9256517 |
| Sargent O'Caine    |    9104521 |
| Darius Bratten     |    9392428 |
| Dani Schistl       |    9907080 |
| Kingston Lowdwell  | 9815982.65 |
| Cathleen Glison    |    9204097 |
| Saloma Cumine      |    9468108 |
| Eli Northidge      |    9461412 |
| Benson Treharne    |    9070640 |
| Electra Lattimore  |    9645308 |
| Hildagarde Chafney |    9164277 |
+--------------------+------------+
15 rows in set (0.00 sec)
```

---

```sql
select Person.Name, Market.Country
from Person
     join License
     join Market
     on Person.NationalTaxID = License.Broker_FK and
        License.Market_FK = Market.StockExchangeTag
where Person.BrokerFlag = 1
limit 15;
```
```
+-----------+------------+
| Name      | Country    |
+-----------+------------+
| Valentijn | Luxembourg |
| Heinrik   | Luxembourg |
| Cart      | Luxembourg |
| Melodee   | Luxembourg |
| Kessia    | Russia     |
| Waring    | Russia     |
| Ailina    | Russia     |
| Felice    | Russia     |
| Brinn     | Russia     |
| Charley   | Russia     |
| Melina    | Colombia   |
| Adams     | Colombia   |
| Melodee   | Colombia   |
| Cart      | Colombia   |
| Adams     | Colombia   |
+-----------+------------+
15 rows in set (0.00 sec)
```
---
```sql
select sellers.Name as Seller, buyers.Name as Buyer
from Person as sellers
     join Hires
     join Person as buyers
     on sellers.NationalTaxID = Hires.SellerUser_FK and
        buyers.NationalTaxID=Hires.BuyerUser_FK
limit 15;
```
```
+-------------------+-------------------+
| Seller            | Buyer             |
+-------------------+-------------------+
| Sosanna Copnar    | Lane Newnham      |
| Sosanna Copnar    | Alford Greenslade |
| Jeremy Seavers    | Clarey Twatt      |
| Hartley Iacavone  | Shep Torricina    |
| Mikael Totterdill | Nyssa Morstatt    |
| Garvey Strover    | Hanni Wones       |
| Merrily Windress  | Melisse Deeks     |
| Padgett Gummoe    | Reiko Barnewell   |
| Padgett Gummoe    | Elane Estabrook   |
| Padgett Gummoe    | Peria Mulhall     |
| Merrilee Sanger   | Mikael Totterdill |
| Merrilee Sanger   | Simon Pettet      |
| Merrilee Sanger   | Sargent O'Caine   |
| Luigi Avramovic   | Jada Yurukhin     |
| Brana Armstrong   | Anna-diana Burn   |
+-------------------+-------------------+
15 rows in set (0.01 sec)
```
---
```sql
select Market.Country, count(Company.TaxID)
from Company 
     join Market 
     on Company.Market_FK=Market.StockExchangeTag
group by Market.Country;
```
```
+---------------+----------------------+
| Country       | count(Company.TaxID) |
+---------------+----------------------+
| Brazil        |                   11 |
| Canada        |                   21 |
| China         |                   73 |
| Colombia      |                    8 |
| Finland       |                    8 |
| Indonesia     |                    6 |
| Japan         |                   15 |
| Luxembourg    |                   13 |
| Malawi        |                   17 |
| Russia        |                    6 |
| Samoa         |                   10 |
| United States |                   12 |
+---------------+----------------------+
12 rows in set (0.00 sec)
```
---
```sql
select Name, count(Person.NationalTaxID)
from Hires
     join Person
     on Hires.BuyerUser_FK = Person.NationalTaxID or
        Hires.SellerUser_FK = Person.NationalTaxID
group by Person.NationalTaxID
order by -count(Person.NationalTaxID)
limit 10;
```
```
+--------------------+-----------------------------+
| Name               | count(Person.NationalTaxID) |
+--------------------+-----------------------------+
| Christin Hoston    |                           8 |
| Owen Labrone       |                           7 |
| Erek Keave         |                           7 |
| Gleda Trillow      |                           7 |
| Erwin Applewhite   |                           6 |
| Merrilee Sanger    |                           6 |
| Tammy Dudny        |                           5 |
| Clementine Flieger |                           5 |
| Rustin Findlow     |                           5 |
| Elane Estabrook    |                           5 |
+--------------------+-----------------------------+
10 rows in set (0.08 sec)
```