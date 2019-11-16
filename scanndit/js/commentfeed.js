function populateExistingComment(comm, showParentPost = false){
  const user = cookieParser(document.cookie).username;
  const commentList = document.querySelector('.all-comments');
  const commentDisplay = document.createElement('div');
  const closeBtn = document.createElement("button");
  closeBtn.className = "close-button";
  closeBtn.classList.add('delete-comment-button');
  closeBtn.innerText = "X";

  commentDisplay.className = 'user-post';
  commentDisplay.innerText = `${comm.user.username}: ${comm.text}`;
  if(comm.user.username === user){
    closeBtn.addEventListener('click',(event)=>{
      event.stopPropagation();
      console.log("DELETE COMMENT");
      deleteComment(document.cookie, comm.id).then(response => {
        event.preventDefault();
        location.reload();
      });
    });
    commentDisplay.appendChild(closeBtn);
  }

  if (showParentPost) {
    const commentAndPostWrapper = document.createElement('div');
    commentAndPostWrapper.classList.add('comment-and-post');
    commentAndPostWrapper.addEventListener('click', function(event) {
      localStorage.setItem(comm.post.id, JSON.stringify(comm.post));
      window.location.href = `/post/#${comm.post.id}`
    });
    commentAndPostWrapper.setAttribute("href", `/post/#${comm.post.id}`);
    const commentParentPostDisplay = document.createElement('div');
    const currentUsername = getCookie('username');
    console.log(currentUsername);
    const postTitle = comm.post.title;
    commentParentPostDisplay.innerText = `${currentUsername} commented on ${postTitle}`;
    commentAndPostWrapper.append(commentParentPostDisplay);
    commentAndPostWrapper.append(commentDisplay);
    commentList.append(commentAndPostWrapper);
  } else {
    commentList.appendChild(commentDisplay);
  }
}


window.addEventListener('DOMContentLoaded', function () {
  let commentfeed = `<div class="all-comments"></div>`;
  let commentList = document.querySelector('.comments-list');
  if (commentList) {
    commentList.innerHTML = commentfeed;
  } else {
    console.log("DANGER, WE DIDN'T FIND A POST LIST ELEMENT, THIS MIGHT CAUSE PROBLEMS");
  }

});
