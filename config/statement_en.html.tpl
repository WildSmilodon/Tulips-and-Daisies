<!-- LEAGUES level1 level2 -->
<div id="statement_back" class="statement_back" style="display:none"></div>
<div class="statement-body">
<p style="text-align: center; font-weight: 700; margin-bottom: 6px;">
      <!-- BEGIN level1 -->
      Defeat the Boss to advance to the next league and compete against the best bots! 
      <!-- END -->
      <!-- BEGIN level2 -->
      Welcome among the best!
      <!-- END -->
</p>

  <!-- GOAL -->
  <div class="statement-section statement-goal">
    <h1>
      <span class="icon icon-goal">&nbsp;</span>
      <span>The Goal</span>
    </h1>
    <div class="statement-goal-content">
      Plant flowers and harvest them for gold. In order to win, have more gold than your opponent does.
      <br>
      <br>
      
    </div>
  </div>
  <!-- RULES -->
  <div class="statement-section statement-rules">
    <h1>
      <span class="icon icon-rules">&nbsp;</span>
      <span>Rules</span>
    </h1>
    <div><div class="statement-rules-content">
        <h2>Definitions</h2>
        <p>
        <ul>
          <li>The game is played on a rectangular field.</li>
		  <li>Players alternate and each turn plant one of their flowers in the field.</li>
		  <li>When <const>4</const> or more flowers of the same type are planted in a line (vertical, horizontal or diagonal), they are harvested and the player earns gold. </li>
        </ul>
        </p>

    </div>
  </div>
  
  <!-- Victory conditions -->
            <div class="statement-victory-conditions">
                <div class="icon victory"></div>
                <div class="blk">
                    <div class="title">Victory Conditions</div>
					 <ul>
						<div class="text">Have more gold than your opponent at the end of the game.</div>
                    </ul>
                    
                </div>
            </div>
            <!-- Lose conditions -->
            <div class="statement-lose-conditions">
                <div class="icon lose"></div>
                <div class="blk">
                    <div class="title">Lose Conditions</div>
                    <ul>
                        <li><div class="text">Output an invalid command or don't respond in time.</div></li>
                        <li><div class="text">Run out of gold.</div></li>
                        <li><div class="text">Have less gold than your opponent at the end of the game.</div></li>
                    </ul>
                </div>
            </div>
  
  
  
  <!-- EXPERT RULES -->
  <div class="statement-section statement-expertrules">
    <h1>
      <span class="icon icon-expertrules">&nbsp;</span>
      <span>Expert Rules</span>
    </h1>
    <div class="statement-expert-rules-content">
	
	
	<h2>Details</h2>
	    <ul>
		  <li>After harvest, grass is found on the tiles.</li>
          <li>The field has <var>fieldHeight</var> rows and <var>fieldWidth</var> columns.</li>
          <li>The top-left corner has position (0,0).</li>
          <li><var>col</var> goes to the right, <var>row</var> increases downwards.</li>
          <li>Each turn you specify where you want to plant your flower by giving <var>row</var> and <var>col</var>. </li>
        </ul>
	
	<h2>Planting costs</h2>
	<p>
	It is allowed to plant your flower anywhere within the field. However, there is a cost associated with planting, which depends on the state of the tile, you want to plant in.
	The cost is deducted from your gold stockpile before planting the flower. You lose, if you do not have enough gold.
	Planting on soil is free, on grass it costs <const>1</const> gold, on rocks <const>5</const> gold and on opponents flower <const>10</const> gold.
	The following table presents the costs of planting your flower on different tiles graphically:
	<br>
	<img src="https://github.com/WildSmilodon/Tulips-and-Daisies/blob/master/statement-images/costs.png?raw=true" alt="This example shows how you earn 7 gold." height="150">
	</p>
	
	<h2>Harvesting flowers</h2>
	<p>
	The flowers are harvested according to the following algorithm. 
	<code>	
        <ul>
          <li>for each direction (horizontal, vertical, both diagonals)
		   	<ul>
          		<li>count the number of adjacent flowers starting from the flower just planted in both ways</li>
		  		<li>if the number of adjacent flowers in this direction is larger or equal to <const>4</const>, all adjacent flowers in this direction are harvested.</li>		  
        	</ul>
		</li>
		</ul>
	</code>
	</p>
	
	<h2>Earning gold</h2>
	<p>
	<ul>
    	<li>The gold earned from <var>N</var> harvested flowers is calculated by summing the first <var>N</var> numbers in the Fibonacci sequence. </li>
		<li>Example: when harvesting <const>4</const> flowers, the player earns 1+1+2+3=<const>7</const> gold, when harvesting <const>6</const> flowers, player earns 1+1+2+3+5+8=<const>20</const> gold. </li>
     </ul>
	 	</p>

<p>		
		
	
	  
    </div>
  </div>
  <!-- EXAMPLES -->
  <div class="statement-section statement-examples">
    <h1>
      <span class="icon icon-example">&nbsp;</span>
      <span>Examples</span>
    </h1>

    <div class="statement-examples-text">
      Here is an example where the Daisy player harvests 4 flowers and earns 1+1+2+3=7 gold.
	  <br>
	  <img src="https://github.com/WildSmilodon/Tulips-and-Daisies/blob/master/statement-images/4d-7g.png?raw=true" alt="This example shows how you earn 4 gold." width="580">
	  <br>
Here is an example where the Tulip player plants a tulip on grass and then harvests 4 flowers and earns 1+1+2+3=7 gold. He first pays the fee for planting on grass (1 gold) and only then earns gold from harvesting, thus turning a profit of 6 gold.
	  <br>
	  <img src="https://github.com/WildSmilodon/Tulips-and-Daisies/blob/master/statement-images/4t-7g.png?raw=true" alt="This example shows how you earn 4 gold." width="560">	  

	  <br>
Here is an example where the Daisy player harvests 5 flowers and earns 1+1+2+3+5=12 gold. 
	  <br>
	  <img src="https://github.com/WildSmilodon/Tulips-and-Daisies/blob/master/statement-images/5d-12g.png?raw=true" alt="This example shows how you earn 4 gold." width="560">	
<br>
Finally, here is an example where the Daisy player harvests 7 flowers in 2 directions and earns 1+1+2+3+5+8+13=33 gold. 
	  <br>
	  <img src="https://github.com/WildSmilodon/Tulips-and-Daisies/blob/master/statement-images/7d-33g.png?raw=true" alt="This example shows how you earn 4 gold." width="560">	


    </div>

  </div>

   
  <!-- WARNING -->
<!--  <div class="statement-section statement-warning">
    <h1>
      <span class="icon icon-warning">&nbsp;</span>
      <span>Note</span>
    </h1>
    <div class="statement-warning-content">
      <b>Don’t forget to run the tests by launching them from the “Test cases” window</b>. You can submit at any time
      to recieve a score against the training validators. <b>You can submit as many times as you like</b>. Your most
      recent submission will be used for the final ranking.<br>
      <br>
      <strong>Warning:</strong> the validation tests used to compute the final score are not the same as the ones used
      during the event.
      Harcoded solutions will not score highly.<br>
      <br>
      Don't hesitate to change the viewer's options to help debug your code (<img style="opacity:.8;background:#20252a;"
        height="18" src="https://www.codingame.com/servlet/fileservlet?id=3463235186409" width="18">).
    </div>
  </div>
-->
  
  <!-- PROTOCOL -->
  <div class="statement-section statement-protocol">
    <h1>
      <span class="icon icon-protocol">&nbsp;</span>
      <span>Game Input</span>
    </h1>
    <!-- Protocol block -->
    <div class="blk">
      <div class="text">The program must first read the initialization data from standard input. Then, provide to the
        standard output one line with the location of the tile you want to plant in.</div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Input</div>
      <div class="text">
	  Initialization input:
        <p><span class="statement-lineno">Line 1: </span>two integers <var>fieldWidth</var> and <var>fieldHeight</var></p>
		<p><span class="statement-lineno">Line 2: </span>four integers <var>costSoil</var>, <var>costGrass</var>, <var>costRocks</var>, <var>costFlower</var></p>
		<p><span class="statement-lineno">Line 3: </span>two strings <var>yourFlowers</var> and <var>opponentsFlowers</var></p>
        During each turn:
        <p><span class="statement-lineno">Line 1: </span>an integer <var>turnsLeft</var></p>
		<p><span class="statement-lineno">Line 2: </span>two integers <var>yourGold</var> and <var>opponentGold</var></p>
		<p><span class="statement-lineno"><var>fieldHeight</var> lines: </span> a string <var>gridLine</var> of length <var>fieldWidth</var> representing the tiles encoded by <const>S</const> - soil, <const>G</const> - grass, <const>R</const> - rocks, <const>T</const> - tulip, <const>D</const> - daisy. </p>
      </div>
    </div>

    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Output</div>
      <div class="text">
        <span class="statement-lineno">A single line</span> containing <var>row</var> and <var>col</var> of the tile you want to plant in. A comment can be appended, which will be displayed.
        <br>
      </div>
    </div>
	

	
    <!-- Protocol block -->
    <div class="blk">
      <div class="title">Constraints</div>
      <div class="text">
	  	8 ≤ <var>fieldWidth</var> ≤ 16<br>
	  	8 ≤ <var>fieldHeight</var> ≤ 16<br>
	  	<!--0 ≤ <var>row</var> < <var>fieldHeight</var><br> -->
	  	<!--0 ≤ <var>col</var> < <var>fieldWidth</var><br> -->
	  	You start the game with <const>100</const> gold.</var><br>
	  	Initially, <const>5</const> to <const>10</const> percent of tiles are rocks, the others are soil.</var><br>
	  	There are <const>256</const> turns in the game. </var><br>  
        Response time first turn &le; <const>1000</const> ms <br>
        Response time per turn &le; <const>50</const> ms <br>
       </div>
    </div>
	
	<!-- Protocol block -->
    <div class="blk">
      <div class="title">Source code</div>
      <div class="text">
  The game source code is available at: 
  <a href="https://github.com/WildSmilodon/Tulips-and-Daisies">https://github.com/WildSmilodon/Tulips-and-Daisies</a>.       
        <br>
      </div>
    </div>
  </div>
  
  
  <!-- STORY -->
  <div class="statement-story-background">
    <div class="statement-story-cover" style="background-size: cover; background: url(https://github.com/WildSmilodon/Tulips-and-Daisies/blob/master/statement-images/notebook.png?raw=true)">
      <div class="statement-story" style="min-height: 300px; position: relative">
        <h1>Tulips & Daisies</h1>
        <div class="story-text">
Back in the 1980s, children in school had math notebooks in which the pages were printed by a grid of squares. Ideal for playing Tic-Tac-Toe. Since playing Tic-Tac-Toe gets boing really quick, a new game was invented. It was played on the whole page of the notebook and points were scored if you got 4 in a row. The game ended when the school bell rang or when the page of the notebook was full. Tulips and daisies was inspired by this game.		
		  </div>
      </div>
    </div>
  </div>
</div>