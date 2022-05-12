# Stock Market Database
SQL and design documents for a stock market database.

Created by Jonathan Moore, Charles Jackson, and Kairo Sauceda for [CS 4347: Database Systems](https://catalog.utdallas.edu/2021/undergraduate/courses/cs4347).

## ER Diagram

![ER Diagram](https://user-images.githubusercontent.com/1783464/168152702-a599000e-d8c6-467e-a543-712a88e2dbc0.png)


## Entity Dictionary

**Entity:** Bank Account

**Description:** Stores money.

**Attributes:**

- Account #: Integer, a unique number that the account gets when it it opened by a Person
- Assets: Double, the amount of money being stored

**Primary Key:** Account #

---

**Entity:** Broker

**Description:** A specialized Person that can participate in the stock market by assisting Users to buy and sell stocks.

**Attributes:**

- Price Per Transaction: Double, the amount that the Broker charges the User to buy or sell 1 stock
- % Profit: Double, any extra income that the Broker may get from fees or commissions

---

**Entity:** City

**Description:** A populated area with buildings where companies like to build their headquarters and stock trading is legal.

**Attributes:**

- Latitude: Double, angle from the equator
- Longitude: Double, angle from the prime meridian
- Name: varchar(80), their common name
- Province: varchar(80), The province that contains the City
- Country: varchar(80), The country that contains the Province

**Primary Key:** combination of Latitude and Longitude

---

**Entity:** Company

**Description:** A business that offers Stocks to the Market.

**Attributes:**

- TaxID: Integer, unique number of company taxes
- Web Page: varchar(30), the internet location
- Phone #: Integer, the telephone location
- Name: varchar(80), the common name
- Address: varchar(80), the physical location
- Reputation: Integer, the relative goodness of the company (higher is better)

**Primary Key:** TaxID

---

**Entity:** Market

**Description:** A platform where people can buy and sell stock the companies offer.

**Attributes:**

- Stock Exchange Tag: varchar(5), 3-5 capital letters

**Primary Key:** Stock Exchange Tag

---

**Entity:** Person

**Description:** A human being.

**Attributes:**

- Name: varchar(80), the common name
- Email Address: varchar(80), personal internet mail location
- National Tax ID: Integer, unique number of personal taxes

**Primary Key:** National Tax ID

---

**Entity:** Stock

**Description:** A share of ownership of a company.

**Attributes:**

- ID: Integer, a unique number that a stock gets when it is offered to the market
- Price: Double, how much the stock is worth in USD
- Dividend: Double, payments made to shareholders in the form of additional shares
- Frequency of Payment: Integer, how often a dividend if paid by an individual stock

**Primary Key:** ID

---

**Entity:** User

**Description:** A specialized Person that can participate in the stock market by buying or selling stocks through a Broker.

## Relation Dictionary

**Relationship:** Hires

**Description:** Two users can hire one broker to trade a stock.

**Attributes:** Time, Broker Profit

**Entities:** User, Broker, Stock

**Cardinality:** 2:1:1

---

**Relationship:** Hosts

**Description:** Each market can host one or more companies.

**Entities:** Market, Company

**Cardinality:** 1:N

---

**Relationship:** Issues license

**Description:** A market can issue multiple licenses to brokers.

**Entities:** Market, Broker

**Cardinality:** 1:N

---

**Relationship:** Lists Stock

**Description:** A user transaction log lists the stocks that are being traded.

**Entities:** Stock, User Transaction Log

**Cardinality:** 1:1

---

**Relationship:** Offers

**Description:** A company can offer one or more stocks. Stocks can only exist if a company offers it.

**Entities:** Company, Stock

**Cardinality:** 1:N

---

**Relationship:** Owns

**Description:** Each person has only one bank account.

**Entities:** Person, Bank Account

**Cardinality:** 1:1

---

**Relationship:** Owns

**Description:** One User can own many stocks.

**Entities:** User, Stock

**Cardinality:** 1:n

---

## Schema Diagram

![Schema Diagram](https://user-images.githubusercontent.com/1783464/168153168-be8beafc-9081-4d3b-84ec-8ae71a10fbac.png)


## Schema Dictionary

**Name:** Company

**Description:** A business that offers stocks to the market.

- **TaxID (PK)**: Integer, unique number of company taxes
- **URL:** varchar(30), the internet location
- **Phone #:** Integer, the telephone location
- **Name:** varchar(80), the common name
- **Address:** varchar(80), the physical location
- **Reputation:** Integer, the relative goodness of the company (higher is better)
- **CFO\_FK** : references Person.NationalTaxID, the person who trades the stocks for this company
- **Market\_FK** : references Market.StockExchangeTag, the market where this company&#39;s stocks are traded

---

**Name:** Hires

**Description:** Two users hire one Broker to trade 1 stock between them.

- **BuyerUser\_FK (PK):** references Person.NationalTaxID, person selling the stock.
- **SellerUser\_FK (PK):** references Person.NationalTaxID, person buying the stock.
- **Broker\_FK (PK):** references Person.NationalTaxID, Broker trading the stock.
- **Stock\_FK (PK):** references Stock.ID, The Stock being traded.
- **Time (PK):** Date Object, The Date and time that the stock was traded
- **Broker\_Profit:** double, how much money the broker made on this gig.

---

**Name:** License

**Description:** A document that a market gives to a broker so the broker can trade on that market.

- **License Number (PK):** Integer, The unique license number
- **Market\_FK (PK):** references Market.StockExchangeTag, the market where the license was issued.
- **Broker\_FK** : references Person.NationalTaxID, the broker the license is issued to

---

**Name:** Market

**Description:** A platform where people can buy and sell stock the companies offer.

- **Stock Exchange Tag (PK):** varchar(5), 3-5 capital letters
- **Longitude:** Double, the value of the longitude of the market&#39;s location
- **Latitude:** Double, the value of the latitude of the market&#39;s location
- **Name:** varchar(80), the city&#39;s common name
- **Province:** varchar(80), the province that contains the city
- **Country:** varchar(80), the country that contains the province
- No foriegn keys

---

**Name:** Person

**Description:** A human being.

- **National Tax ID (PK)**: Integer, unique number of personal taxes
- **Name:** varchar(80), the common name
- **Email Address:** varchar(80), personal internet mail location
- **UserFlag:** Boolean, indicates if the person is a user who wants to buy or sell stock
- **BrokerFlag:** Boolean, indicates if the person is a broker
- **PercentProfit:** Double, the percentage of profit a broker makes from a transaction
- **PricePerTransaction:** Double, the base price per transaction a broker charges
- **Assets:** Double, the amount of money that a person has in their bank account

---

**Name:** Stock

**Description:** A share of ownership of a company.

- **ID (PK):** Integer, a unique number that a stock gets when it is offered to the market
- **Price:** Double, how much the stock is worth in USD
- **Dividend:** Double, payments made to shareholders in the form of additional shares
- **Frequency of Payment:** Integer, how often a dividend if paid by an individual stock
- **Company:** references Company.TaxID, the company associated with the stock
- **Person:** references Person. NationalTaxId. The person who owns the stock.
