# materialized-project
Rails application showcasing materialize design
This is a rails application with materialized design for user interface

Helpful sources in integrating materialize design on Rails:

Tutorial: https://www.webascender.com/blog/tutorial-using-materialize-ruby-rails-simple-form/
Github: https://github.com/mkhairi/materialize-sass
Materialize: http://materializecss.com/forms.html​

Notable edits:

in application css, ad these lines:
	
	 @import "materialize";
	 
	 @import "materialize/extras/nouislider";
	 
	 @import "https://fonts.googleapis.com/icon?family=Material+Icons";

These lines tell css to import necessary libraries for material css layouts to run on your rails project, thus implying a dependency on internet connectivity