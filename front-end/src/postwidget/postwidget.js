document.querySelector('.post-widget').addEventListener('transitionrun', function (event) {
  if (event.target == this && event.propertyName == 'max-height') {
    this.getElementsByClassName('post-widget-submission-box')[0].style.visibility = "hidden";
  }
});

document.querySelector('.post-widget').addEventListener('transitionend', function (event) {
  if (event.target == this && event.propertyName == 'background-color') {
    this.getElementsByClassName('post-widget-submission-box')[0].style.visibility = "visible";
  }
});
