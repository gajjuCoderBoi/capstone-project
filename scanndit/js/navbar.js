function handleSignupResponse(signupResponse) {
  let user = { token: signupResponse.token, username: signupResponse.username };
  login(user);
  $('#signupModal').modal('hide');
}

function isLoggedIn() {
  cookieObject = cookieParser(document.cookie);
  return (typeof cookieObject.access_token != 'undefined');
}

function login(user) {
  document.cookie = `access_token=${user.token}; path=/`;
  document.cookie = `username=${user.username}; path=/`;
  console.log(`${user.username} has logged in...`);
  successfulLogin();
}

function successfulLogin() {
  document.body.classList.add('logged-in');
  document.body.classList.remove('logged-out');
  document.querySelector('#dropdown-username').innerText = cookieParser(document.cookie).username;
  document.querySelector('#navbar-profile').hidden = false;
  document.querySelector('#navbar-login').hidden = true;
}

function logout() {
  document.cookie = `access_token= ; expires = Thu, 01 Jan 1970 00:00:00 GMT; path=/`;
  document.cookie = `username= ; expires = Thu, 01 Jan 1970 00:00:00 GMT; path=/`;
  document.body.classList.add('logged-out');
  document.body.classList.remove('logged-in');
  document.querySelector('#dropdown-username').innerText = '';
  document.querySelector('#navbar-profile').hidden = true;
  document.querySelector('#navbar-login').hidden = false;
  if (document.querySelector('.navbar-collapse').classList.contains('show')) {
    document.querySelector('.navbar-collapse').classList.remove('show');
  }
}

let navbarHtml = `
<nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light navbar-custom">
  <a class="navbar-brand" href="/"><img class="logo" src="/logo2.png"><span class="navbar-logo-text">scandit<span></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavDropdown">
    <ul class="navbar-nav ml-auto u-custom" id="navbar-profile">
      <li class="nav-item dropdown">
        <a id="dropdown-username" class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></a>
        <div class="dropdown-menu mr-auto dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="/profile/">My Profile</a>
          <a id="logout-button" class="dropdown-item" href="#">Logout</a>
        </div>
      </li>
    </ul>
    <div class="navbar-nav ml-auto my-2 my-lg-0" id="navbar-login">
      <button id="navbar-login-button" class="btn btn-outline-primary my-2 my-sm-0" type="button" data-toggle="modal" data-target="#loginModal">Log In</button>
      <button class="btn btn-primary my-2 my-sm-0" type="button" data-toggle="modal" data-target="#signupModal">Sign Up</button>
    </div>
  </div>
</nav>

<!-- Login Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Login</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <form class="login-form">
              <input type="text" id="user-login" class="fadeIn second login-setting" name="login" placeholder="  Username">
              <input type="password" id="user-password" class="fadeIn third login-setting" name="login" placeholder="  Password">
          </form>
          <span class="error-message" hidden></span>
      </div>
      <div class="modal-footer">
        <button type="button" id="close-btn" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" id="login-btn" class="btn btn-primary" >Sign In</button>
      </div>
    </div>
  </div>
</div>

<!-- Sign Up Modal -->
<div class="modal fade" id="signupModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">Sign Up</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
          <form class="signup-form">
              <input type="text" id="signup-email" class="fadeIn second signup-setting" name="signup-email" placeholder="  Email">
              <input type="text" id="signup-username" class="fadeIn second signup-setting" name="signup-username" placeholder="  Username">
              <input type="password" id="signup-password" class="fadeIn third signup-setting" name="signup-password" placeholder="  Password">
          </form>
          <span class="error-message" hidden></span>
      </div>
      <div class="modal-footer">
        <button type="button" id="close-btn" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" id="signup-btn" class="btn btn-primary" >Sign In</button>
      </div>
    </div>
  </div>
</div>
`;

window.addEventListener('DOMContentLoaded', function () {
  let navbarWrapper = document.createElement('div');
  navbarWrapper.innerHTML = navbarHtml;
  document.body.prepend(navbarWrapper);
  const logoutButton = document.querySelector('#logout-button');

  // v CHECK LOGIN STATE
  if (isLoggedIn()) {
    successfulLogin();
  } else {
    logout();
  }
  // ^ CHECK LOGIN STATE

  logoutButton.addEventListener('click', function () {
    logout();
    if (window.location.pathname == '/profile/') {
      window.location.href = "/";
      location.reload();
    }
    else{
      location.reload();
    }
  });

});
