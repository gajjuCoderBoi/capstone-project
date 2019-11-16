// const API_ENDPOINT_BASE = 'http://thesi.generalassemb.ly:8080/';
const API_ENDPOINT_BASE = 'http://localhost:8080/scandit/';

// document.cookie parser
// obtained from https://gist.github.com/rendro/525bbbf85e84fa9042c2
function cookieParser(cookie){
  return document.cookie
  .split(';')
  .reduce((res, c) => {
    const [key, val] = c.trim().split('=').map(decodeURIComponent)
    try {
      return Object.assign(res, { [key]: JSON.parse(val) })
    } catch (e) {
      return Object.assign(res, { [key]: val })
    }
  }, {});
}

function buildHeader(access_token = null) {
  let header = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  }
  if (access_token) { header.Authorization = 'Bearer ' + access_token }
  return(header);
}

// Returns response as json, or returns an error object
async function callApiAndReturnResponseOrThrowError(path, method, access_token = null, body = null) {
  let fetchOptions = {
    method: method,
    headers: buildHeader(access_token),
  };
  if (body) { fetchOptions.body = body };
  let response = await fetch(`${API_ENDPOINT_BASE}${path}`, fetchOptions).then(function (response) {
    if (!response.ok) {
      // TODO: HANDLE BAD RESPONSE BETTER
      console.log(`${method} to ${path} received a bad response. HANDLE THIS BETTER`);
      throw Error(response.statusText);
    }
    return response;
    }).then(function(response) {
      return response.json();
    }).catch(err => err);
    return response;
}

// get comments by user
async function getCommentsByUser(access_token) {
  let response = await callApiAndReturnResponseOrThrowError('user/comment', 'GET', access_token);
  return response;
}

// get posts by user
async function getPostsByUser(access_token) {
  let response = await callApiAndReturnResponseOrThrowError('user/post', 'GET', access_token);
  return response;
}

// get profile
async function getProfile(access_token) {
  let response = await callApiAndReturnResponseOrThrowError('profile', 'GET', access_token);
  return response;
}

// creates or updates profile
async function createOrUpdateProfile(access_token, altEmail, mobileNumber, address) {
  let profileInfo = {
    additionalEmail: altEmail,
    mobile: mobileNumber,
    address: address
  };
  let response = await callApiAndReturnResponseOrThrowError('profile', 'POST', access_token, JSON.stringify(profileInfo));

  return response;
}

// Loading all posts
async function getAllPosts()
{
  let response = await callApiAndReturnResponseOrThrowError('post/list', 'GET');
  return response;
}

// loading all comments based on comment id
async function getAllComments(postId){
  let response = await callApiAndReturnResponseOrThrowError(`post/${postId}/comment`, 'GET');
  return response;
}

// new user sign up
async function signUp(email, password, username){
  let user = {
    email: email,
    password: password,
    username: username,
  };
  let response = await callApiAndReturnResponseOrThrowError('signup', 'POST', null, JSON.stringify(user));
  return response;
}

// Existing User Login
async function loginUser(user){
  let response = await callApiAndReturnResponseOrThrowError('login', 'POST', null, JSON.stringify(user));
  return response;
}

//create new Post
async function createNewPost(userTitle, userDescr, auth){
  let userAuth = cookieParser(auth);
  let post = {
    title: userTitle,
    description: userDescr
  }
  let response = await callApiAndReturnResponseOrThrowError('post', 'POST', userAuth.access_token, JSON.stringify(post));
  return response;
}

// post a comment
async function postComment(comment, auth, postId){
  let userAuth = cookieParser(auth);
  let response = await callApiAndReturnResponseOrThrowError(`comment/${postId}`, 'POST', userAuth.access_token, JSON.stringify(comment));
  return response;
}

// delete comment
async function deleteComment(auth, commentId){
  let userAuth = cookieParser(auth);
  let response = await callApiAndReturnResponseOrThrowError(`comment/${commentId}`, 'DELETE', userAuth.access_token);
  return response;
}
