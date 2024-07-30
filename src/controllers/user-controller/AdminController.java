
package usercontroller;
class AdminController extends UserController{
  final UserProvider userProvider; 

  public AdminController(UserProvider userProvider){
    this.userProvider= userProvider;
  }

  public List<User> getAllUsers() {
    return userProvider.getAllUsers();
  }

}