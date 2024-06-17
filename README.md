# Track a Magic the Gathering collection

Uses MTG's API to retrieve card data.
MTG API is included as I created two custom methods in the CardAPI class,
one for retrieving all instances of a card (multiple printings), 
the other for retrieving the first **non-promo** version of a card
**(where multiverse ID is >0)**

I created this app from the ground up, all of the code written by myself
in conjunction with a Udemy course I was taking on Spring Boot/Spring/
Hibernate. I developed the CRUD API first, centered around the 
`MTGCollectionTrackerRestController`.  

Then I expanded to include HTML forms which again I wrote from the ground
up, using Bootstrap for the design, and added pages built around each form 
in the `MTGCollectionTrackerFormController`.  

I then merged the functionality of the previous two controllers in the 
`MTGCollectionTrackerAppController`. In addition I added security configurations
and priveleges, stored in the same MySQL database as the collection itself.
The passwords were hashed using BCrypt. The `SecurityConfig` class utilizes JDBC to 
extract the credentials.  

Finally I added a simple `Login` controller for mapping the login page, which
the app reroutes you to when opened.  

```java
@Controller
public class MTGCollectionTrackerLoginController {

    @GetMapping("/loginPage")
    public String showLoginPage() {

        return "Forms/LoginForm";
    }
}
```

permission to use MTG API:  
The MIT License (MIT)

Copyright (c) 2016 magicthegathering.io

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
