function onviewUserPostsClick() {
  let viewUserPostsButton = document.querySelector('#view-user-posts-button');
  let viewUserCommentsButton = document.querySelector('#view-user-comments-button');
  let postList = document.querySelector('.post-list');
  let commentsList = document.querySelector('.comments-list');
  viewUserPostsButton.disabled = true;
  viewUserCommentsButton.disabled = false;
  // postList.hidden = false;
  // commentsList.hidden = true;
  postList.classList.add('is-visible');
  commentsList.classList.remove('is-visible');
}

function onviewUserCommentsClick() {
  let viewUserPostsButton = document.querySelector('#view-user-posts-button');
  let viewUserCommentsButton = document.querySelector('#view-user-comments-button');
  let postList = document.querySelector('.post-list');
  let commentsList = document.querySelector('.comments-list');
  viewUserPostsButton.disabled = false;
  viewUserCommentsButton.disabled = true;
  // postList.hidden = true;
  // commentsList.hidden = false;
  postList.classList.remove('is-visible');
  commentsList.classList.add('is-visible');
}

function getCookie(name) {
  var value = "; " + document.cookie;
  var parts = value.split("; " + name + "=");
  if (parts.length == 2) return parts.pop().split(";").shift();
}

function handleSaveProfileResponse(profileResponse) {
  if (profileResponse.name == "Error") {
    // Notify user that their profile update was not actually saved
    // Might need to redirect them to some login thing
  } else if (typeof profileResponse.id == 'number') {
    // set values of inputs
    populateProfile(profileResponse.additionalEmail, profileResponse.mobile, profileResponse.address);
    makeProfileNotEditable("edit");
  } else {
    // dont expect to end up here, but...
    console.log('didnt expect to end up here, not sure whats happening');
  }
}

function makeProfileNotEditable(buttonState) {
  document.querySelectorAll(".profile-user-info-field").forEach(function (el) {
    el.classList.remove("editable");
  });
  document.querySelectorAll(".profile-field").forEach(function (el) {
    el.setAttribute('disabled', 'disabled');
    el.classList.remove("editable");
  });
  if (buttonState == 'create') {
    document.querySelector('.profile-user-info').setAttribute('hidden', 'true');
  }
  displayButtons(buttonState);
}

function makeProfileEditable() {
  document.querySelectorAll(".profile-user-info-field").forEach(function (el) {
    el.classList.add("editable");
  });
  document.querySelectorAll(".profile-field").forEach(function (el) {
    el.removeAttribute('disabled');
    el.classList.add("editable");
  });
  document.querySelector('.profile-user-info').removeAttribute('hidden');
  displayButtons("saveCancel");
}

function saveProfile(altEmail, mobileNumber, address) {
  let access_token = getCookie('access_token');
  createOrUpdateProfile(access_token, altEmail, mobileNumber, address)
    .then(response => {
      handleSaveProfileResponse(response);
    });
}

function resetInputFieldsToLastValue() {
  let profileAltEmail = document.querySelector('.profile-alt-email');
  let profileMobileNumber = document.querySelector('.profile-mobile-number');
  let profileAddress = document.querySelector('.profile-address');
  profileAltEmail.value = profileAltEmail.dataset.altEmail || '';
  profileMobileNumber.value = profileMobileNumber.dataset.mobileNumber || '';
  profileAddress.value = profileAddress.dataset.address || '';
}

function populateProfile(altEmail, mobileNumber, address) {
  let profileAltEmail = document.querySelector('.profile-alt-email');
  let profileMobileNumber = document.querySelector('.profile-mobile-number');
  let profileAddress = document.querySelector('.profile-address');
  profileAltEmail.setAttribute('data-alt-email', altEmail);
  profileMobileNumber.setAttribute('data-mobile-number', mobileNumber);
  profileAddress.setAttribute('data-address', address);
  profileAltEmail.value = altEmail;
  profileMobileNumber.value = mobileNumber;
  profileAddress.value = address;
}

function displayButtons(buttonType) {
  document.querySelectorAll('.profile-buttons-wrapper').forEach(el => el.setAttribute('hidden', true));
  switch (buttonType) {
    case "create":
      document.querySelector('.profile-create-button-wrapper').removeAttribute('hidden');
      break;
    case "edit":
      document.querySelector('.profile-edit-button-wrapper').removeAttribute('hidden');
      break;
    case "saveCancel":
      document.querySelector('.profile-save-cancel-button-wrapper').removeAttribute('hidden');
      break;
    default:
  }
}

async function handleProfileResponse(profileResponse) {
  if (profileResponse.name == "Error") {
    displayButtons('create');
  } else if (typeof profileResponse.id == 'number') {
    // populate fields and show edit profile button
    populateProfile(profileResponse.additionalEmail, profileResponse.mobile, profileResponse.address);
    let profileUserInfo = document.querySelector('.profile-user-info');
    displayButtons('edit');
    profileUserInfo.setAttribute('data-profile-exists', 'true');
    profileUserInfo.removeAttribute('hidden');
  } else {
    // dont expect to end up here, but...
    console.log('didnt expect to end up here, not sure whats happening');
  }
  return;
}






// ON DOM CONTENT LOADED
window.addEventListener('DOMContentLoaded', function () {
  let profileEditButton = document.querySelector('.profile-edit-button');
  let profileCreateButton = document.querySelector('.profile-create-button');
  let profileCancelButton = document.querySelector('.profile-cancel-button');
  let profileSaveButton = document.querySelector('.profile-save-button');

  let altEmailInput = document.querySelector('.profile-alt-email');
  let mobileNumberInput = document.querySelector('.profile-mobile-number');
  let addressInput = document.querySelector('.profile-address');

  let viewUserPostsButton = document.querySelector('#view-user-posts-button');
  let viewUserCommentsButton = document.querySelector('#view-user-comments-button');

  viewUserPostsButton.addEventListener('click', onviewUserPostsClick);
  viewUserCommentsButton.addEventListener('click', onviewUserCommentsClick);

  // console.log(document.querySelectorAll('.profile-user-info-field'));
  document.querySelectorAll('.profile-user-info-field').forEach(function(el) {
    el.addEventListener('click', function() {
      let inputToFocus = this.getElementsByTagName('input')[0];
      if (document.activeElement != inputToFocus) {
        let originalValue = inputToFocus.value;
        inputToFocus.value = '';
        inputToFocus.focus();
        inputToFocus.value = originalValue;
      }
    });
  });

  profileEditButton.addEventListener('click', function() {
    makeProfileEditable();
  });

  profileCreateButton.addEventListener('click', function() {
    console.log('create!!!!!!!!!');
    makeProfileEditable();
  });

  profileCancelButton.addEventListener('click', function() {
    resetInputFieldsToLastValue();
    let profileUserInfo = document.querySelector('.profile-user-info');
    let nextButtonState = (profileUserInfo.dataset.profileExists == 'true') ? 'edit' : 'create';
    makeProfileNotEditable(nextButtonState);
  });

  profileSaveButton.addEventListener('click', function() {
    console.log('save!!!!!!!!!');
    saveProfile(altEmailInput.value, mobileNumberInput.value, addressInput.value);
  });

  window.pagesDisplayed = 0;
  let access_token = getCookie("access_token");

  // Fetch profile info or redirect to homepage
  if (typeof(access_token) == "string") {
    document.querySelector('.profile-username').innerText = getCookie('username');
    getProfile(access_token).then(response => {
      return handleProfileResponse(response);
    }).then(() => {
      let access_token = getCookie("access_token");
      return getPostsByUser(access_token).then(response => {
        if (response.name == "Error") {
          logout();
          window.location.href = "/";
        }
        return response;
      });
    }).then(response => {
      if (response.name == "Error") {
        console.log("got an error trying to get posts for user, perhaps the access_token is expired");
      } else {
        appendToHomepageFeed(response, window.pagesDisplayed + 1);
      }
    });
    getCommentsByUser(access_token).then(res =>{
      res.forEach(element => {
        populateExistingComment(element, true)
      });
    });
  } else {
    window.location.href = '/';
  }

});
