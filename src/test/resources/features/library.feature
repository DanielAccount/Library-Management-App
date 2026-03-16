Feature: Library Management App
    Scenario: Store books in the library
        Given Book "The Lord of the Rings" by "J R R Tolkein" with ISBN number "003"
        When I store the book in library
        Then I am able to retrieve the book by the ISBN number