# Editor.md

![](https://pandao.github.io/editor.md/images/logos/editormd-logo-180x180.png)

![](https://img.shields.io/github/stars/pandao/editor.md.svg) ![](https://img.shields.io/github/forks/pandao/editor.md.svg) ![](https://img.shields.io/github/tag/pandao/editor.md.svg) ![](https://img.shields.io/github/release/pandao/editor.md.svg) ![](https://img.shields.io/github/issues/pandao/editor.md.svg) ![](https://img.shields.io/bower/v/editor.md.svg)

**ç›®å½• (Table of Contents)**

[TOCM]

[TOC]

# Heading 1
## Heading 2               
### Heading 3
#### Heading 4
##### Heading 5
###### Heading 6
# Heading 1 link [Heading link](https://github.com/pandao/editor.md "Heading link")
## Heading 2 link [Heading link](https://github.com/pandao/editor.md "Heading link")
### Heading 3 link [Heading link](https://github.com/pandao/editor.md "Heading link")
#### Heading 4 link [Heading link](https://github.com/pandao/editor.md "Heading link") Heading link [Heading link](https://github.com/pandao/editor.md "Heading link")
##### Heading 5 link [Heading link](https://github.com/pandao/editor.md "Heading link")
###### Heading 6 link [Heading link](https://github.com/pandao/editor.md "Heading link")

#### æ ‡é¢˜ï¼ˆç”¨åº•çº¿çš„å½¢å¼ï¼‰Heading (underline)

This is an H1
=============

This is an H2
-------------

### å­—ç¬¦æ•ˆæžœå’Œæ¨ªçº¿ç­‰
                
----

~~åˆ é™¤çº¿~~ <s>åˆ é™¤çº¿ï¼ˆå¼€å¯è¯†åˆ«HTMLæ ‡ç­¾æ—¶ï¼‰</s>
*æ–œä½“å­—*      _æ–œä½“å­—_
**ç²—ä½“**  __ç²—ä½“__
***ç²—æ–œä½“*** ___ç²—æ–œä½“___

ä¸Šæ ‡ï¼šX<sub>2</sub>ï¼Œä¸‹æ ‡ï¼šO<sup>2</sup>

**ç¼©å†™(åŒHTMLçš„abbræ ‡ç­¾)**

> å³æ›´é•¿çš„å•è¯æˆ–çŸ­è¯­çš„ç¼©å†™å½¢å¼ï¼Œå‰ææ˜¯å¼€å¯è¯†åˆ«HTMLæ ‡ç­¾æ—¶ï¼Œå·²é»˜è®¤å¼€å¯

The <abbr title="Hyper Text Markup Language">HTML</abbr> specification is maintained by the <abbr title="World Wide Web Consortium">W3C</abbr>.

### å¼•ç”¨ Blockquotes

> å¼•ç”¨æ–‡æœ¬ Blockquotes

å¼•ç”¨çš„è¡Œå†…æ··åˆ Blockquotes
                    
> å¼•ç”¨ï¼šå¦‚æžœæƒ³è¦æ’å…¥ç©ºç™½æ¢è¡Œ`å³<br />æ ‡ç­¾`ï¼Œåœ¨æ’å…¥å¤„å…ˆé”®å…¥ä¸¤ä¸ªä»¥ä¸Šçš„ç©ºæ ¼ç„¶åŽå›žè½¦å³å¯ï¼Œ[æ™®é€šé“¾æŽ¥](http://localhost/)ã€‚

### é”šç‚¹ä¸Žé“¾æŽ¥ Links

[æ™®é€šé“¾æŽ¥](http://localhost/)

[æ™®é€šé“¾æŽ¥å¸¦æ ‡é¢˜](http://localhost/ "æ™®é€šé“¾æŽ¥å¸¦æ ‡é¢˜")

ç›´æŽ¥é“¾æŽ¥ï¼š<https://github.com>

[é”šç‚¹é“¾æŽ¥][anchor-id] 

[anchor-id]: http://www.this-anchor-link.com/

[mailto:test.test@gmail.com](mailto:test.test@gmail.com)

GFM a-tail link @pandao  é‚®ç®±åœ°å€è‡ªåŠ¨é“¾æŽ¥ test.test@gmail.com  www@vip.qq.com

> @pandao

### å¤šè¯­è¨€ä»£ç é«˜äº® Codes

#### è¡Œå†…ä»£ç  Inline code

æ‰§è¡Œå‘½ä»¤ï¼š`npm install marked`

#### ç¼©è¿›é£Žæ ¼

å³ç¼©è¿›å››ä¸ªç©ºæ ¼ï¼Œä¹Ÿåšä¸ºå®žçŽ°ç±»ä¼¼ `<pre>` é¢„æ ¼å¼åŒ–æ–‡æœ¬ ( Preformatted Text ) çš„åŠŸèƒ½ã€‚

    <?php
        echo "Hello world!";
    ?>
    
é¢„æ ¼å¼åŒ–æ–‡æœ¬ï¼š

    | First Header  | Second Header |
    | ------------- | ------------- |
    | Content Cell  | Content Cell  |
    | Content Cell  | Content Cell  |

#### JSä»£ç ã€€

```javascript
function test() {
	console.log("Hello world!");
}
 
(function(){
    var box = function() {
        return box.fn.init();
    };

    box.prototype = box.fn = {
        init : function(){
            console.log('box.init()');

			return this;
        },

		add : function(str) {
			alert("add", str);

			return this;
		},

		remove : function(str) {
			alert("remove", str);

			return this;
		}
    };
    
    box.fn.init.prototype = box.fn;
    
    window.box =box;
})();

var testBox = box();
testBox.add("jQuery").remove("jQuery");
```

#### HTML ä»£ç  HTML codes

```html
<!DOCTYPE html>
<html>
    <head>
        <mate charest="utf-8" />
        <meta name="keywords" content="Editor.md, Markdown, Editor" />
        <title>Hello world!</title>
        <style type="text/css">
            body{font-size:14px;color:#444;font-family: "Microsoft Yahei", Tahoma, "Hiragino Sans GB", Arial;background:#fff;}
            ul{list-style: none;}
            img{border:none;vertical-align: middle;}
        </style>
    </head>
    <body>
        <h1 class="text-xxl">Hello world!</h1>
        <p class="text-green">Plain text</p>
    </body>
</html>
```

### å›¾ç‰‡ Images

Image:

![](https://pandao.github.io/editor.md/examples/images/4.jpg)

> Follow your heart.

![](https://pandao.github.io/editor.md/examples/images/8.jpg)

> å›¾ä¸ºï¼šåŽ¦é—¨ç™½åŸŽæ²™æ»©

å›¾ç‰‡åŠ é“¾æŽ¥ (Image + Link)ï¼š

[![](https://pandao.github.io/editor.md/examples/images/7.jpg)](https://pandao.github.io/editor.md/images/7.jpg "æŽå¥é¦–å¼ ä¸“è¾‘ã€Šä¼¼æ°´æµå¹´ã€‹å°é¢")

> å›¾ä¸ºï¼šæŽå¥é¦–å¼ ä¸“è¾‘ã€Šä¼¼æ°´æµå¹´ã€‹å°é¢
                
----

### åˆ—è¡¨ Lists

#### æ— åºåˆ—è¡¨ï¼ˆå‡å·ï¼‰Unordered Lists (-)
                
- åˆ—è¡¨ä¸€
- åˆ—è¡¨äºŒ
- åˆ—è¡¨ä¸‰
     
#### æ— åºåˆ—è¡¨ï¼ˆæ˜Ÿå·ï¼‰Unordered Lists (*)

* åˆ—è¡¨ä¸€
* åˆ—è¡¨äºŒ
* åˆ—è¡¨ä¸‰

#### æ— åºåˆ—è¡¨ï¼ˆåŠ å·å’ŒåµŒå¥—ï¼‰Unordered Lists (+)
                
+ åˆ—è¡¨ä¸€
+ åˆ—è¡¨äºŒ
    + åˆ—è¡¨äºŒ-1
    + åˆ—è¡¨äºŒ-2
    + åˆ—è¡¨äºŒ-3
+ åˆ—è¡¨ä¸‰
    * åˆ—è¡¨ä¸€
    * åˆ—è¡¨äºŒ
    * åˆ—è¡¨ä¸‰

#### æœ‰åºåˆ—è¡¨ Ordered Lists (-)
                
1. ç¬¬ä¸€è¡Œ
2. ç¬¬äºŒè¡Œ
3. ç¬¬ä¸‰è¡Œ

#### GFM task list

- [x] GFM task list 1
- [x] GFM task list 2
- [ ] GFM task list 3
    - [ ] GFM task list 3-1
    - [ ] GFM task list 3-2
    - [ ] GFM task list 3-3
- [ ] GFM task list 4
    - [ ] GFM task list 4-1
    - [ ] GFM task list 4-2
                
----
                    
### ç»˜åˆ¶è¡¨æ ¼ Tables

| é¡¹ç›®        | ä»·æ ¼   |  æ•°é‡  |
| --------   | -----:  | :----:  |
| è®¡ç®—æœº      | $1600   |   5     |
| æ‰‹æœº        |   $12   |   12   |
| ç®¡çº¿        |    $1    |  234  |
                    
First Header  | Second Header
------------- | -------------
Content Cell  | Content Cell
Content Cell  | Content Cell 

| First Header  | Second Header |
| ------------- | ------------- |
| Content Cell  | Content Cell  |
| Content Cell  | Content Cell  |

| Function name | Description                    |
| ------------- | ------------------------------ |
| `help()`      | Display the help window.       |
| `destroy()`   | **Destroy your computer!**     |

| Left-Aligned  | Center Aligned  | Right Aligned |
| :------------ |:---------------:| -----:|
| col 3 is      | some wordy text | $1600 |
| col 2 is      | centered        |   $12 |
| zebra stripes | are neat        |    $1 |

| Item      | Value |
| --------- | -----:|
| Computer  | $1600 |
| Phone     |   $12 |
| Pipe      |    $1 |
                
----

#### ç‰¹æ®Šç¬¦å· HTML Entities Codes

&copy; &  &uml; &trade; &iexcl; &pound;
&amp; &lt; &gt; &yen; &euro; &reg; &plusmn; &para; &sect; &brvbar; &macr; &laquo; &middot; 

X&sup2; Y&sup3; &frac34; &frac14;  &times;  &divide;   &raquo;

18&ordm;C  &quot;  &apos;

[========]

### Emojiè¡¨æƒ… :smiley:

> Blockquotes :star:

#### GFM task lists & Emoji & fontAwesome icon emoji & editormd logo emoji :editormd-logo-5x:

- [x] :smiley: @mentions, :smiley: #refs, [links](), **formatting**, and <del>tags</del> supported :editormd-logo:;
- [x] list syntax required (any unordered or ordered list supported) :editormd-logo-3x:;
- [x] [ ] :smiley: this is a complete item :smiley:;
- [ ] []this is an incomplete item [test link](#) :fa-star: @pandao; 
- [ ] [ ]this is an incomplete item :fa-star: :fa-gear:;
    - [ ] :smiley: this is an incomplete item [test link](#) :fa-star: :fa-gear:;
    - [ ] :smiley: this is  :fa-star: :fa-gear: an incomplete item [test link](#);
 
#### åæ–œæ  Escape

\*literal asterisks\*

[========]
            
### ç§‘å­¦å…¬å¼ TeX(KaTeX)

$$E=mc^2$$

è¡Œå†…çš„å…¬å¼$$E=mc^2$$è¡Œå†…çš„å…¬å¼ï¼Œè¡Œå†…çš„$$E=mc^2$$å…¬å¼ã€‚

$$x > y$$

$$\(\sqrt{3x-1}+(1+x)^2\)$$
                    
$$\sin(\alpha)^{\theta}=\sum_{i=0}^{n}(x^i + \cos(f))$$

å¤šè¡Œå…¬å¼ï¼š

```math
\displaystyle
\left( \sum\_{k=1}^n a\_k b\_k \right)^2
\leq
\left( \sum\_{k=1}^n a\_k^2 \right)
\left( \sum\_{k=1}^n b\_k^2 \right)
```

```katex
\displaystyle 
    \frac{1}{
        \Bigl(\sqrt{\phi \sqrt{5}}-\phi\Bigr) e^{
        \frac25 \pi}} = 1+\frac{e^{-2\pi}} {1+\frac{e^{-4\pi}} {
        1+\frac{e^{-6\pi}}
        {1+\frac{e^{-8\pi}}
         {1+\cdots} }
        } 
    }
```

```latex
f(x) = \int_{-\infty}^\infty
    \hat f(\xi)\,e^{2 \pi i \xi x}
    \,d\xi
```

### åˆ†é¡µç¬¦ Page break

> Print Test: Ctrl + P

[========]

### ç»˜åˆ¶æµç¨‹å›¾ Flowchart

```flow
st=>start: ç”¨æˆ·ç™»é™†
op=>operation: ç™»é™†æ“ä½œ
cond=>condition: ç™»é™†æˆåŠŸ Yes or No?
e=>end: è¿›å…¥åŽå°

st->op->cond
cond(yes)->e
cond(no)->op
```

[========]
                    
### ç»˜åˆ¶åºåˆ—å›¾ Sequence Diagram
                    
```seq
Andrew->China: Says Hello 
Note right of China: China thinks\nabout it 
China-->Andrew: How are you? 
Andrew->>China: I am good thanks!
```

### End