-- Conventions:
-- SQL keywords are all lowercase.
-- Table names are singular.
-- Attributes and table names are UpperCamelCase.
-- Foreign keys have _FK at the end.
-- Space before ( near identifiers but not types.

create table Company (
       TaxID      integer       not null,
       Url        varchar(30),    
       Phone      char(10),         
       Name       varchar(80)   not null,
       Address    varchar(80),
       Reputation integer,
       CFO_FK     integer       not null,
       Market_FK  varchar(5)    not null,
       primary key (TaxID)
);

create table Hires (
       BuyerUser_FK  integer    not null,
       SellerUser_FK integer    not null,
       Broker_FK     integer    not null,
       Stock_FK      integer    not null,
       Time          date       not null,
       BrokerProfit  double     not null,
       primary key (BuyerUser_FK, SellerUser_FK, Broker_FK, Stock_FK, Time)
);

create table License (
       LicenseNumber integer    not null,
       Market_FK     varchar(5) not null,
       Broker_FK     integer    not null,
       primary key (LicenseNumber, Market_FK)
);

create table Market (
       StockExchangeTag varchar(5)  not null,
       Longitude        double      not null,
       Latitude         double      not null,
       Name             varchar(80) not null,
       Province         varchar(80),
       Country          varchar(80) not null,
       primary key (StockExchangeTag)
);

create table Person (
       NationalTaxID       integer      not null,
       Name                varchar(80)  not null,
       EmailAddress        varchar(80),
       UserFlag            boolean      not null,
       BrokerFlag          boolean      not null,
       PercentProfit       double,
       PricePerTransaction double,
       Assets              double,
       primary key (NationalTaxID)
);

create table Stock (
       ID                 integer   not null,
       Price              double    not null,
       Dividend           double,
       FrequencyOfPayment integer,
       Company_FK         integer   not null,
       Person_FK          integer,
       primary key (ID)
);

-- foreign key stuff
alter table Company add foreign key (CFO_FK)        references Person (NationalTaxID);
alter table Company add foreign key (Market_FK)     references Market (StockExchangeTag);
alter table Hires   add foreign key (BuyerUser_FK)  references Person (NationalTaxID);
alter table Hires   add foreign key (SellerUser_FK) references Person (NationalTaxID);
alter table Hires   add foreign key (Broker_FK)     references Person (NationalTaxID);
alter table Hires   add foreign key (Stock_FK)      references Stock (ID);
alter table License add foreign key (Market_FK)     references Market (StockExchangeTag);
alter table License add foreign key (Broker_FK)     references Person (NationalTaxID);
alter table Stock   add foreign key (Company_FK)    references Company (TaxID);
alter table Stock   add foreign key (Person_FK)    references Person (NationalTaxID);
