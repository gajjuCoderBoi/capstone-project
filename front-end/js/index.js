
window.addEventListener('DOMContentLoaded', (event) => {
  $(window).scroll(function() {
    if($(window).scrollTop() + $(window).height() == $(document).height()) {
      getAllPosts()
        .then(data => appendToHomepageFeed(data, window.pagesDisplayed + 1));
    }
  });

  const closeButton = document.querySelector('#close-btn');
  const userLogin = document.querySelector('#user-login');
  const userPassword = document.querySelector('#user-password');
  const loginButton = document.querySelector('#login-btn');

  const signupEmail = document.querySelector('#signup-email');
  const signupUsername = document.querySelector('#signup-username');
  const signupPassword = document.querySelector('#signup-password');
  const signupButton = document.querySelector('#signup-btn');

  const loadMoreButton = document.querySelector('#load-more-button');

  const postButton = document.querySelector('#post-btn');
  const postTitle = document.querySelector('#new-title');
  const postText = document.querySelector('#new-post');

  loadMoreButton.addEventListener('click',()=>{
    getAllPosts()
      .then(data => appendToHomepageFeed(data, window.pagesDisplayed + 1));
  });

  signupButton.addEventListener('click', function () {
    signUp(signupEmail.value, signupPassword.value, signupUsername.value)
      .then(response => {
        handleSignupResponse(response);
      });
  })

  //User login event
  loginButton.addEventListener('click', ()=>{
    const user = {
      "email" : userLogin.value,
      "password" : userPassword.value
    }
    loginUser(user).then(res=> {
      let response = res;
      let errorMessage = document.querySelector('.error-message');
      if(response.token === undefined){
        console.log(response.message);
        errorMessage.innerText = response.message;
        errorMessage.hidden = false;

      }
      else{
        let user = { token: response.token, username: response.username };
        login(user);
        errorMessage.hidden = true;
        closeButton.click();
        errorMessage.innerText = "";
        successfulLogin();


      }
    })
  })

  //Close out of login modal
  closeButton.addEventListener('click',()=> document.querySelector('.error-message').hidden = true);


  // Create posts from post widget
  let postwidget = document.querySelector('.post-widget');
  let postWidgetExpandButton = document.querySelector('#post-widget-collaped-logged-in-button');
  postWidgetExpandButton.addEventListener('click', (e) => {
    postwidget.classList.remove('collapsed');
    postwidget.classList.add('expanded');
  });
  let submitPostButton = document.querySelector('#submit-post-button');
  let postSubmissionTitle = document.querySelector('.post-submission-title');
  let postSubmissionBody = document.querySelector('.post-submission-body');
  document.querySelector('#cancel-and-collapse-button').addEventListener('click', (e) => {
    postSubmissionTitle.value = '';
    postSubmissionBody.value = '';
    document.querySelector('.post-widget-submission-box').style.visibility = 'hidden';
    postwidget.classList.add('collapsed');
    postwidget.classList.remove('expanded');
  });
  document.querySelector('#submit-post-button').addEventListener('click',()=>{
    createNewPost(postSubmissionTitle.value, postSubmissionBody.value, document.cookie).then(res => {
      let postFeed = document.querySelector('.all-posts');
      let postDisplay = postSetUp(res);
      postFeed.prepend(postDisplay);
    }).then(res => {
      postSubmissionTitle.value = '';
      postSubmissionBody.value = '';
    });
  })

  // Get first page of posts and append to all-posts div
  window.pagesDisplayed = 0;
  getAllPosts()
    .then(data => appendToHomepageFeed(data, window.pagesDisplayed + 1));

});
