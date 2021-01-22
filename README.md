# KotlinBank



[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/othneildrew/Best-README-Template">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
 </a>

  <h3 align="center">KotlinBank</h3>

<p align="center">
    A Bank from devs for devs!
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
It is an API Rest of a principal function of Bank in BackEnd.
The principals feature in this API are:

Here's why:
* Create Account
* Make Transfer
* Make Deposit
* Get Accounts
* Get Client


### Built With

This section should list any major frameworks that you built your project using. Leave any add-ons/plugins for the acknowledgements section. Here are a few examples.
* [SpringBoot](https://spring.io/projects/spring-boot)
* [H2 Database Engine](https://www.h2database.com/html/main.html)
* [Kotlin](https://kotlinlang.org/docs/reference/)



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Verify your Maven (The version of maven built is 3.6.3)
  ```sh
  mvn -version
  ```

### Installation


2. Clone the repo
   ```sh
   git clone https://github.com/DanFonseca/KotlinBank
   ```
4. You Can Import the PostMan Collections at root in this project folder (collect.json)
3. Import the project in your favorite IDE and Play =)

<!-- USAGE EXAMPLES -->
## Usage

* ##### 1 Create an Account

```sh
   POST - localhost:8080/v1/account
   ```
* Body

 ```json
  {
      "amount": 400,
      "client": {
          "cpf": "16026086013",
          "name": "Jorge"
      }
  }
```
  
  * ##### 2 Get an Account by CPF
```sh
   GET - localhost:8080/v1/account/user/16026086013
   ```


* ##### 3 Get Account By AccountId
```sh
   GET - localhost:8080/v1/account/1
   ```

* ##### 4 Make A Transfer
Let's create two accounts for make a transfer

```sh
   POST - localhost:8080/v1/account
   ```
```json
   {
       "amount": 2500,
       "client": {
           "cpf": "70491031017",
           "name": "Maria Rodrigues"
       }
   }
   ```
Account two
```json
   {
       "amount": 1300,
       "client": {
           "cpf": "51704084040",
           "name": "João Pedro"
       }
   }
   ```

- Now, we can make a transfer:

```sh
   POST - localhost:8080/v1/account/transfer
   ```
```json
{
    "accountIdFrom": 1,
    "accountIdTo":2,
    "amount": 100
}
   ```
- #####accountIdFrom: Account that will be debit amount
- #####accountIdTo: Account that will be receive the amount

- Response 200 with account data amount updated

```json
{
    "Account From": {
        "id": 1,
        "amount": 2400.00,
        "client": {
           "cpf": "70491031017",
           "name": "Maria Rodrigues"
        }
    },
    "Account To": {
        "id": 2,
        "amount": 1400.00,
        "client": {
            "cpf": "51704084040",
           "name": "João Pedro"
        }
    }
}
   ```

* ##### 5 Make A Deposit
Lets deposit a little of Maria's account  =)
 > You can deposit until R$ 2000
```sh
   POST - localhost:8080/v1/account/deposit
   ```
```json
   {
       "accountId": 1,
       "amount": 250
   }
   ```

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Daniel Fonseca  - daniel.freitas.ms1@gmail.com


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]:https://www.linkedin.com/in/%F0%9F%91%A8%F0%9F%8F%BD%E2%80%8D%F0%9F%92%BB-daniel-freitas-28666a134/