var sceneHtml = document.createElement('canvas');//html:create
//var sceneHtml = document.getElementById('canvas');//html:create
var sceneDomObj = sceneHtml.getContext('2d');

    var ScreenW=screen.width;
    //console.log(ScreenW);
    //alert(ScreenW);
    var DocumentW=document.documentElement.clientWidth;
var ScreenH=screen.height;
var DocumentH=document.documentElement.clientHeight;
sceneHtml.width = ScreenW;
    sceneHtml.height = ScreenH;

document.getElementById('main').appendChild(sceneHtml);//html:render

//................................
    //兼容
        //硬件
            //PC PAD PHONE...
                //响应式
    var AutoImageheroSrc;
    var AutoImagemonsterSrc;

    var text;
    if(ScreenW <= 479){
        //image
            AutoImageheroSrc = 'images/440/1pac-man.png';
            AutoImagemonsterSrc = 'images/440/1shadow.png';
        //text
            text = 10;
    }
    if(480 <= ScreenW <= 767){
        //console.log(ScreenW);
        //alert(ScreenW);
        //image
            AutoImageheroSrc = 'images/720/2pac-man.png';
            AutoImagemonsterSrc = 'images/720/2shadow.png';
        //text
            text = 15;
    }
    if(768 <= ScreenW <= 959){
        //image
            AutoImageheroSrc = 'images/880/3pac-man.png';
            AutoImagemonsterSrc = 'images/880/3shadow.png';
        //text
            text = 20;
    }
    if(960 <= ScreenW <= 1199){
        //image
            AutoImageheroSrc = 'images/1100/4pac-man.png';
            AutoImagemonsterSrc = 'images/1100/4shadow.png';
        //text
            text = 30;
    }
    if(1200 <= ScreenW){
        //console.log(ScreenW);
        //alert(ScreenW);
        //image
            AutoImageheroSrc = 'images/1280/5pac-man.png';
            AutoImagemonsterSrc = 'images/1280/5shadow.png';
        //text
            text = 40;
    }

    var backImageReady = false;
    var  backImage = new Image();
        backImage.src = 'images/maze.png';
        backImage.onload = function(){
            backImageReady = true;
            back.image = {
                'obj': backImage,
                'width':backImage.width,
                'height':backImage.height
            };
            //alert(back.image.src);
        }

    var heroImageReady= false;
    var heroImage = new Image();
        heroImage.src = AutoImageheroSrc;
        heroImage.onload = function(){
            heroImageReady = true;
            hero.image = {
                'obj': heroImage,
                'width':heroImage.width,
                'height':heroImage.height
            };
        }

    var monsterImageReady= false;
    var monsterImage = new Image();
        monsterImage.src = AutoImagemonsterSrc;
        monsterImage.onload = function(){
            monsterImageReady = true;
            monster.image = {
                'obj': monsterImage,
                'width':monsterImage.width,
                'height':monsterImage.height
            };
        }

    var back = {
        image:{},
    }
    var hero = {
        image:{},
        x:sceneHtml.width/2,
        y:sceneHtml.height/2,
        speed:500
    }
    var monster = {
        image:{},
        x:0,
        y:0,
    }

    var score = {
        catchMonster:0,
    }


    var past = Date.now();


        //CLICK
        var click = {};
        document.getElementsByTagName('canvas')[0].addEventListener('click',function(e){
            click['x'] = e.clientX;
            click['y'] = e.clientY;
        });

    var update = function(time){
        //移动
                //CLICK
                if(click['x']){//left
                    //console.log(click['x']);
                    if(hero.x <= 0){
                        hero.x = 0;
                    }
                    if(hero.y <= 0){
                        hero.y = 0;
                    }
                    if(hero.x >= sceneHtml.width - hero.image.width){
                        hero.x = sceneHtml.width - hero.image.width;
                    }
                    if(hero.y >= sceneHtml.height - hero.image.height){
                        hero.y = sceneHtml.height - hero.image.height;
                    }

                    if(hero.x > click['x']){
                        hero.x -= time * hero.speed;
                    }
                    if(hero.y > click['y']){
                        hero.y -= hero.speed * time;
                    }
                    if(hero.x < click['x']){
                        hero.x += time * hero.speed;
                    }
                    if(hero.y < click['y']){
                        hero.y += hero.speed * time;
                    }

                }
            //触碰
            if(Math.abs(hero.x - monster.x)<= hero.image.width &&
                Math.abs(hero.y - monster.y)<= hero.image.height
            ){
                delete click['x'];
                delete click['y'];

                ++score.catchMonster;
                hero.x = sceneHtml.width/2;
                hero.y = sceneHtml.height/2;
                monster.x = Math.random() * (sceneHtml.width - monsterImage.width);
                monster.y = Math.random() * (sceneHtml.height - monsterImage.height);
            }
    }
    var render = function(){
        //if(backImageReady){
        //    sceneDomObj.drawImage(back.image.obj,0,0)
        //}
        sceneDomObj.fillStyle="#ff5a00";
        sceneDomObj.fillRect(0,0,sceneHtml.width,sceneHtml.height);

        if(heroImageReady){
            sceneDomObj.drawImage(hero.image.obj,hero.x,hero.y,hero.image.width,hero.image.height);
        }
        if(monsterImageReady){
            sceneDomObj.drawImage(monster.image.obj,monster.x,monster.y,monster.image.width,monster.image.height);
        }

            sceneDomObj.fillStyle = 'rgba(255,255,255,1)';
//            sceneDomObj.font = 1.5*text + 'px Helvetica';
            sceneDomObj.font = '50% Helvetica';
            sceneDomObj.textAlign = 'left';
            sceneDomObj.textBaseline="top";
        sceneDomObj.fillText('catch monster:' + score.catchMonster,0,0);
    }
    var game = function(){
        //效果的实现实际上是数据的更新
            //内部：scene elements 数据的更新
                //时间
                    var now = Date.now();
                    var time = (now - past)/1000;
                //空间
                    update(time);
            //内部：scene elements 数据的更新 => 外部：我们看到的 听到的...变化
                render();
            //外部：我们看到的 听到的...变化

                past = now;
                requestAnimationFrame(game);
    }
    game();



    requestAnimationFrame = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.msRequestAnimationFrame || window.webkitRequestAnimationFrame;
