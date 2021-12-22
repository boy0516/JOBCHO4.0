/*===== EXPANDER MENU  =====*/ 
const showMenu = (toggleId, navbarId, bodyId)=>{
  const toggle = document.getElementById(toggleId),
  navbar = document.getElementById(navbarId),
  bodypadding = document.getElementById(bodyId)

  if(toggle && navbar){
    toggle.addEventListener('click', ()=>{
      navbar.classList.toggle('expander-right')

      bodypadding.classList.toggle('body-pd-right')
    })
  }
}
showMenu('nav-toggle-right','navbar-right','body-pd-right')

/*===== LINK ACTIVE  =====*/ 
const linkColor = document.querySelectorAll('.nav__link-right')
function colorLink(){
  linkColor.forEach(l=> l.classList.remove('active-right'))
  this.classList.add('active-right')
}
linkColor.forEach(l=> l.addEventListener('click', colorLink))


/*===== COLLAPSE MENU  =====*/ 
const linkCollapse = document.getElementsByClassName('collapse__link-right')
var i

for(i=0;i<linkCollapse.length;i++){
  linkCollapse[i].addEventListener('click', function(){
    const collapseMenu = this.nextElementSibling
    console.log(collapseMenu)
    
    collapseMenu.classList.toggle('showCollapse-right')

    const rotate = collapseMenu.previousElementSibling
    rotate.classList.toggle('rotate-right')
  })
}


