/*===== EXPANDER MENU  =====*/ 
const hideMenuLeft = (toggleId, navbarId, bodyId)=>{
  const toggle = document.getElementById(toggleId),
  navbar = document.getElementById(navbarId),
  bodypadding = document.getElementById(bodyId)

  if(toggle && navbar){
    toggle.addEventListener('click', (e)=>{
      navbar.classList.add('expander-left')
      e.stopPropagation();
      bodypadding.classList.add('body-pd-left')
      bodypadding.classList.remove('body-content')
    })
  }
}
hideMenuLeft('nav-toggle-left','navbar-left','body-pd-left')

const showMenuLeft = (toggleId, navbarId, bodyId)=>{
  const toggle = document.getElementById(toggleId),
  navbar = document.getElementById(navbarId),
  bodypadding = document.getElementById(bodyId)

  if(toggle && navbar.getElementsByClassName('expander-left')){
    navbar.addEventListener('click', ()=>{
      navbar.classList.remove('expander-left')
      console.log("실행되면 안됨")
      bodypadding.classList.remove('body-pd-left')
      bodypadding.classList.add('body-content')
    })
  }
}
showMenuLeft('nav-toggle-left','navbar-left','body-pd-left')

/*===== LINK ACTIVE  =====*/ 
const linkColorLeft = document.querySelectorAll('.nav__link-left')
function colorLink(){
  linkColorLeft.forEach(l=> l.classList.remove('active-left'))
  this.classList.add('active-left')
}
linkColorLeft.forEach(l=> l.addEventListener('click', colorLink))


/*===== COLLAPSE MENU  =====*/ 
const linkCollapseLeft = document.getElementsByClassName('collapse__link-left')
var i

for(i=0;i<linkCollapse.length;i++){
  linkCollapseLeft[i].addEventListener('click', function(){
    const collapseMenu = this.nextElementSibling
    collapseMenu.classList.toggle('showCollapse-left')

    const rotate = collapseMenu.previousElementSibling
    rotate.classList.toggle('rotate-left')
  })
}


