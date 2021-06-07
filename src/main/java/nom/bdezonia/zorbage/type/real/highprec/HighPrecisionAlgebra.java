/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.type.real.highprec;

import java.lang.Integer;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebra.*;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.algorithm.NumberWithin;
import nom.bdezonia.zorbage.algorithm.ScaleHelper;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.function.Function3;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionAlgebra
	implements
		OrderedField<HighPrecisionAlgebra,HighPrecisionMember,HighPrecisionMember>,
		Norm<HighPrecisionMember,HighPrecisionMember>,
		RealConstants<HighPrecisionMember>,
		RealUnreal<HighPrecisionMember,HighPrecisionMember>,
		Conjugate<HighPrecisionMember>,
		Scale<HighPrecisionMember,HighPrecisionMember>,
		ScaleByHighPrec<HighPrecisionMember>,
		ScaleByRational<HighPrecisionMember>,
		ScaleByDouble<HighPrecisionMember>,
		ScaleComponents<HighPrecisionMember, HighPrecisionMember>,
		Trigonometric<HighPrecisionMember>,
		Hyperbolic<HighPrecisionMember>,
		InverseTrigonometric<HighPrecisionMember>,
		InverseHyperbolic<HighPrecisionMember>,
		Roots<HighPrecisionMember>,
		Power<HighPrecisionMember>,
		Tolerance<HighPrecisionMember,HighPrecisionMember>,
		Exponential<HighPrecisionMember>,
		ScaleByOneHalf<HighPrecisionMember>,
		ScaleByTwo<HighPrecisionMember>,
		MiscFloat<HighPrecisionMember>
{
	private static MathContext CONTEXT = new MathContext(24, RoundingMode.HALF_EVEN);
	private static final BigDecimal THREE = BigDecimal.valueOf(3);
	
	public static MathContext getContext() {
		return CONTEXT;
	}
	
	public static int getPrecision() {
		return CONTEXT.getPrecision();
	}
	
	public static void setPrecision(int decimalPlaces) {
		if (decimalPlaces < 1)
			throw new IllegalArgumentException("number of decimal places must be > 0");
		int maxPlaces = Math.min(Math.min(PI_STR.length()-2, E_STR.length()-2), Math.min(GAMMA_STR.length()-2, PHI_STR.length()-2));
		if (decimalPlaces > maxPlaces)
			throw new IllegalArgumentException("precision too high: beyond max accuracy of "+maxPlaces+" decimal places");
		CONTEXT = new MathContext(decimalPlaces, RoundingMode.HALF_EVEN);
	}

	// Source: wolfram alpha to 4000 digits on 5/25/19

	public static final String PI_STR    = "3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117067982148086513282306647093844609550582231725359408128481117450284102701938521105559644622948954930381964428810975665933446128475648233786783165271201909145648566923460348610454326648213393607260249141273724587006606315588174881520920962829254091715364367892590360011330530548820466521384146951941511609433057270365759591953092186117381932611793105118548074462379962749567351885752724891227938183011949129833673362440656643086021394946395224737190702179860943702770539217176293176752384674818467669405132000568127145263560827785771342757789609173637178721468440901224953430146549585371050792279689258923542019956112129021960864034418159813629774771309960518707211349999998372978049951059731732816096318595024459455346908302642522308253344685035261931188171010003137838752886587533208381420617177669147303598253490428755468731159562863882353787593751957781857780532171226806613001927876611195909216420198938095257201065485863278865936153381827968230301952035301852968995773622599413891249721775283479131515574857242454150695950829533116861727855889075098381754637464939319255060400927701671139009848824012858361603563707660104710181942955596198946767837449448255379774726847104047534646208046684259069491293313677028989152104752162056966024058038150193511253382430035587640247496473263914199272604269922796782354781636009341721641219924586315030286182974555706749838505494588586926995690927210797509302955321165344987202755960236480665499119881834797753566369807426542527862551818417574672890977772793800081647060016145249192173217214772350141441973568548161361157352552133475741849468438523323907394143334547762416862518983569485562099219222184272550254256887671790494601653466804988627232791786085784383827967976681454100953883786360950680064225125205117392984896084128488626945604241965285022210661186306744278622039194945047123713786960956364371917287467764657573962413890865832645995813390478027590099465764078951269468398352595709825822620522489407726719478268482601476990902640136394437455305068203496252451749399651431429809190659250937221696461515709858387410597885959772975498930161753928468138268683868942774155991855925245953959431049972524680845987273644695848653836736222626099124608051243884390451244136549762780797715691435997700129616089441694868555848406353422072225828488648158456028506016842739452267467678895252138522549954666727823986456596116354886230577456498035593634568174324112515076069479451096596094025228879710893145669136867228748940560101503308617928680920874760917824938589009714909675985261365549781893129784821682998948722658804857564014270477555132379641451523746234364542858444795265867821051141354735739523113427166102135969536231442952484937187110145765403590279934403742007310578539062198387447808478489683321445713868751943506430218453191048481005370614680674919278191197939952061419663428754440643745123718192179998391015919561814675142691239748940907186494231961567945208095146550225231603881930142093762137855956638937787083039069792077346722182562599661501421503068038447734549202605414665925201497442850732518666002132434088190710486331734649651453905796268561005508106658796998163574736384052571459102897064140110971206280439039759515677157700420337869936007230558763176359421873125147120532928191826186125867321579198414848829164470609575270695722091756711672291098169091528017350671274858322287183520935396572512108357915136988209144421006751033467110314126711136990865851639831501970165151168517143765761835155650884909989859982387345528331635507647918535893226185489632132933089857064204675259070915481416549859461637180270981994309924488957571282890592323326097299712084433573265489382391193259746366730583604142813883032038249037589852437441702913276561809377344403070746921120191302033038019762110110044929321516084244485963766983895228684783123552658213144957685726243344189303968642624341077322697802807318915441101044682325271620105265227211166040";

	// Source: wolfram alpha to 4000 digits on 5/25/19
	
	public static final String E_STR     = "2.718281828459045235360287471352662497757247093699959574966967627724076630353547594571382178525166427427466391932003059921817413596629043572900334295260595630738132328627943490763233829880753195251019011573834187930702154089149934884167509244761460668082264800168477411853742345442437107539077744992069551702761838606261331384583000752044933826560297606737113200709328709127443747047230696977209310141692836819025515108657463772111252389784425056953696770785449969967946864454905987931636889230098793127736178215424999229576351482208269895193668033182528869398496465105820939239829488793320362509443117301238197068416140397019837679320683282376464804295311802328782509819455815301756717361332069811250996181881593041690351598888519345807273866738589422879228499892086805825749279610484198444363463244968487560233624827041978623209002160990235304369941849146314093431738143640546253152096183690888707016768396424378140592714563549061303107208510383750510115747704171898610687396965521267154688957035035402123407849819334321068170121005627880235193033224745015853904730419957777093503660416997329725088687696640355570716226844716256079882651787134195124665201030592123667719432527867539855894489697096409754591856956380236370162112047742722836489613422516445078182442352948636372141740238893441247963574370263755294448337998016125492278509257782562092622648326277933386566481627725164019105900491644998289315056604725802778631864155195653244258698294695930801915298721172556347546396447910145904090586298496791287406870504895858671747985466775757320568128845920541334053922000113786300945560688166740016984205580403363795376452030402432256613527836951177883863874439662532249850654995886234281899707733276171783928034946501434558897071942586398772754710962953741521115136835062752602326484728703920764310059584116612054529703023647254929666938115137322753645098889031360205724817658511806303644281231496550704751025446501172721155519486685080036853228183152196003735625279449515828418829478761085263981395599006737648292244375287184624578036192981971399147564488262603903381441823262515097482798777996437308997038886778227138360577297882412561190717663946507063304527954661855096666185664709711344474016070462621568071748187784437143698821855967095910259686200235371858874856965220005031173439207321139080329363447972735595527734907178379342163701205005451326383544000186323991490705479778056697853358048966906295119432473099587655236812859041383241160722602998330535370876138939639177957454016137223618789365260538155841587186925538606164779834025435128439612946035291332594279490433729908573158029095863138268329147711639633709240031689458636060645845925126994655724839186564209752685082307544254599376917041977780085362730941710163434907696423722294352366125572508814779223151974778060569672538017180776360346245927877846585065605078084421152969752189087401966090665180351650179250461950136658543663271254963990854914420001457476081930221206602433009641270489439039717719518069908699860663658323227870937650226014929101151717763594460202324930028040186772391028809786660565118326004368850881715723866984224220102495055188169480322100251542649463981287367765892768816359831247788652014117411091360116499507662907794364600585194199856016264790761532103872755712699251827568798930276176114616254935649590379804583818232336861201624373656984670378585330527583333793990752166069238053369887956513728559388349989470741618155012539706464817194670834819721448889879067650379590366967249499254527903372963616265897603949857674139735944102374432970935547798262961459144293645142861715858733974679189757121195618738578364475844842355558105002561149239151889309946342841393608038309166281881150371528496705974162562823609216807515017772538740256425347087908913729172282861151591568372524163077225440633787593105982676094420326192428531701878177296023541306067213604600038966109364709514141718577701418060644363681546444005331608778314317444081194942297559931401188868331483280270655383300469329011574414756314000";

	// Source: wolfram alpha to 4000 digits on 5/25/19
	
	public static final String GAMMA_STR = "0.5772156649015328606065120900824024310421593359399235988057672348848677267776646709369470632917467495146314472498070824809605040144865428362241739976449235362535003337429373377376739427925952582470949160087352039481656708532331517766115286211995015079847937450857057400299213547861466940296043254215190587755352673313992540129674205137541395491116851028079842348775872050384310939973613725530608893312676001724795378367592713515772261027349291394079843010341777177808815495706610750101619166334015227893586796549725203621287922655595366962817638879272680132431010476505963703947394957638906572967929601009015125195950922243501409349871228247949747195646976318506676129063811051824197444867836380861749455169892792301877391072945781554316005002182844096053772434203285478367015177394398700302370339518328690001558193988042707411542227819716523011073565833967348717650491941812300040654693142999297779569303100503086303418569803231083691640025892970890985486825777364288253954925873629596133298574739302373438847070370284412920166417850248733379080562754998434590761643167103146710722370021810745044418664759134803669025532458625442225345181387912434573501361297782278288148945909863846006293169471887149587525492366493520473243641097268276160877595088095126208404544477992299157248292516251278427659657083214610298214617951957959095922704208989627971255363217948873764210660607065982561990102880756125199137511678217643619057058440783573501580056077457934213144988500786415171615194565706170432450750081687052307890937046143066848179164968425491504967243121837838753564894950868454102340601622508515583867234944187880440940770106883795111307872023426395226920971608856908382511378712836820491178925944784861991185293910293099059255266917274468920443869711147174571574573203935209122316085086827558890109451681181016874975470969366671210206304827165895049327314860874940207006742590918248759621373842311442653135029230317517225722162832488381124589574386239870375766285513033143929995401853134141586212788648076110030152119657800681177737635016818389733896639868957932991456388644310370608078174489957958324579418962026049841043922507860460362527726022919682995860988339013787171422691788381952984456079160519727973604759102510995779133515791772251502549293246325028747677948421584050759929040185576459901862692677643726605711768133655908815548107470000623363725288949554636971433012007913085552639595497823023144039149740494746825947320846185246058776694882879530104063491722921858008706770690427926743284446968514971825678095841654491851457533196406331199373821573450874988325560888873528019019155089688554682592454445277281730573010806061770113637731824629246600812771621018677446849595142817901451119489342288344825307531187018609761224623176749775564124619838564014841235871772495542248201615176579940806296834242890572594739269638633838743805471319676429268372490760875073785283702304686503490512034227217436689792848629729088926789777032624623912261888765300577862743606094443603928097708133836934235508583941126709218734414512187803276150509478055466300586845563152454605315113252818891079231491311032344302450933450003076558648742229717700331784539150566940159988492916091140029486902088485381697009551566347055445221764035862939828658131238701325358800625686626926997767737730683226900916085104515002261071802554659284938949277595897540761559933782648241979506418681437881718508854080367996314239540091964388750078900000627997942809886372992591977765040409922037940427616817837156686530669398309165243227059553041766736640116792959012930537449718308004275848635083808042466735093559832324116969214860649892763624432958854873789701489713343538448002890466650902845376896223983048814062730540879591189670574938544324786914808533770264067758081275458731117636478787430739206642011251352727499617545053085582356683068322917676677041035231535032510124656386156706449847132695969330167866138333333441657900605867497103646895174569597181553764078377650184278345991842015995431449047725552306147670166";
	
	// Source: wolfram alpha to 4000 digits on 5/25/19
	
	public static final String PHI_STR   = "1.618033988749894848204586834365638117720309179805762862135448622705260462818902449707207204189391137484754088075386891752126633862223536931793180060766726354433389086595939582905638322661319928290267880675208766892501711696207032221043216269548626296313614438149758701220340805887954454749246185695364864449241044320771344947049565846788509874339442212544877066478091588460749988712400765217057517978834166256249407589069704000281210427621771117778053153171410117046665991466979873176135600670874807101317952368942752194843530567830022878569978297783478458782289110976250030269615617002504643382437764861028383126833037242926752631165339247316711121158818638513316203840052221657912866752946549068113171599343235973494985090409476213222981017261070596116456299098162905552085247903524060201727997471753427775927786256194320827505131218156285512224809394712341451702237358057727861600868838295230459264787801788992199027077690389532196819861514378031499741106926088674296226757560523172777520353613936210767389376455606060592165894667595519004005559089502295309423124823552122124154440064703405657347976639723949499465845788730396230903750339938562102423690251386804145779956981224457471780341731264532204163972321340444494873023154176768937521030687378803441700939544096279558986787232095124268935573097045095956844017555198819218020640529055189349475926007348522821010881946445442223188913192946896220023014437702699230078030852611807545192887705021096842493627135925187607778846658361502389134933331223105339232136243192637289106705033992822652635562090297986424727597725655086154875435748264718141451270006023890162077732244994353088999095016803281121943204819643876758633147985719113978153978074761507722117508269458639320456520989698555678141069683728840587461033781054443909436835835813811311689938555769754841491445341509129540700501947754861630754226417293946803673198058618339183285991303960720144559504497792120761247856459161608370594987860069701894098864007644361709334172709191433650137157660114803814306262380514321173481510055901345610118007905063814215270930858809287570345050780814545881990633612982798141174533927312080928972792221329806429468782427487401745055406778757083237310975915117762978443284747908176518097787268416117632503861211291436834376702350371116330725869883258710336322238109809012110198991768414917512331340152733843837234500934786049792945991582201258104598230925528721241370436149102054718554961180876426576511060545881475604431784798584539731286301625448761148520217064404111660766950597757832570395110878230827106478939021115691039276838453863333215658296597731034360323225457436372041244064088826737584339536795931232213437320995749889469956564736007295999839128810319742631251797141432012311279551894778172691415891177991956481255800184550656329528598591000908621802977563789259991649946428193022293552346674759326951654214021091363018194722707890122087287361707348649998156255472811373479871656952748900814438405327483781378246691744422963491470815700735254570708977267546934382261954686153312095335792380146092735102101191902183606750973089575289577468142295433943854931553396303807291691758461014609950550648036793041472365720398600735507609023173125016132048435836481770484818109916024425232716721901893345963786087875287017393593030133590112371023917126590470263494028307668767436386513271062803231740693173344823435645318505813531085497333507599667787124490583636754132890862406324563953572125242611702780286560432349428373017255744058372782679960317393640132876277012436798311446436947670531272492410471670013824783128656506493434180390041017805339505877245866557552293915823970841772983372823115256926092995942240000560626678674357923972454084817651973436265268944888552720274778747335983536727761407591712051326934483752991649980936024617844267572776790019191907038052204612324823913261043271916845123060236278935454324617699757536890417636502547851382463146583363833760235778992672988632161858395903639981838458276449124598093704305555961379734326";
	
	public HighPrecisionAlgebra() { }
	
	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> EQ =
		new Function2<Boolean,HighPrecisionMember,HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) == 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isEqual() {
		return EQ;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> NEQ =
			new Function2<Boolean,HighPrecisionMember,HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) != 0;
		}
	};
		
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isNotEqual() {
		return NEQ;
	}

	@Override
	public HighPrecisionMember construct() {
		return new HighPrecisionMember();
	}

	@Override
	public HighPrecisionMember construct(HighPrecisionMember other) {
		return new HighPrecisionMember(other);
	}

	@Override
	public HighPrecisionMember construct(String s) {
		return new HighPrecisionMember(s);
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> ASSIGN =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember from, HighPrecisionMember to) {
			to.set(from);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> assign() {
		return ASSIGN;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> ADD =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV( a.v().add(b.v()) );
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> add() {
		return ADD;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> SUB =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV( a.v().subtract(b.v()) );
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> subtract() {
		return SUB;
	}

	private final Procedure1<HighPrecisionMember> ZER =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			a.primitiveInit();
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> zero() {
		return ZER;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> NEG =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV( a.v().negate() );
		}
	};

	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> negate() {
		return NEG;
	}

	private final Procedure1<HighPrecisionMember> UNITY =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			a.setV( BigDecimal.ONE );
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> unity() {
		return UNITY;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> INV =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV( BigDecimal.ONE.divide(a.v(), CONTEXT) );
		}
	};

	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> invert() {
		return INV;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> DIVIDE =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV( a.v().divide(b.v(), CONTEXT) );
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> divide() {
		return DIVIDE;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> LESS =
			new Function2<Boolean, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) < 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isLess() {
		return LESS;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> LE =
			new Function2<Boolean, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) <= 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isLessEqual() {
		return LE;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> GREAT =
			new Function2<Boolean, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) > 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isGreater() {
		return GREAT;
	}

	private final Function2<Boolean,HighPrecisionMember,HighPrecisionMember> GE =
			new Function2<Boolean, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a, HighPrecisionMember b) {
			return compare().call(a, b) >= 0;
		}
	};
	
	@Override
	public Function2<Boolean,HighPrecisionMember,HighPrecisionMember> isGreaterEqual() {
		return GE;
	}

	private final Function2<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> CMP =
			new Function2<java.lang.Integer, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public java.lang.Integer call(HighPrecisionMember a, HighPrecisionMember b) {
			return a.v().compareTo(b.v());
		}
	};
	
	@Override
	public Function2<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> compare() {
		return CMP;
	}

	private final Function1<java.lang.Integer,HighPrecisionMember> SIG =
			new Function1<Integer, HighPrecisionMember>()
	{
		@Override
		public Integer call(HighPrecisionMember a) {
			return a.v().signum();
		}
	};
	
	@Override
	public Function1<java.lang.Integer,HighPrecisionMember> signum() {
		return SIG;
	}
	
	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> MUL =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV( a.v().multiply(b.v(), CONTEXT) );
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> multiply() {
		return MUL;
	}

	private final Procedure3<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> POWER =
			new Procedure3<Integer, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(Integer power, HighPrecisionMember a, HighPrecisionMember b) {
			if (power == 0 && a.v().equals(BigDecimal.ZERO)) {
				throw new IllegalArgumentException("0^0 is not a number");
			}
			else
				b.setV( a.v().pow(power) );
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> power() {
		return POWER;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> ABS =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV( a.v().abs() );
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> abs() {
		return ABS;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> NORM =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			abs().call(a, b);
		}
	};

	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> norm() {
		return NORM;
	}

	private final Procedure1<HighPrecisionMember> PI_ =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			BigDecimal pi = new BigDecimal(PI_STR.substring(0, CONTEXT.getPrecision()+2));
			a.setV(pi);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> PI() {
		return PI_;
	}

	private final Procedure1<HighPrecisionMember> E_ =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			BigDecimal e = new BigDecimal(E_STR.substring(0, CONTEXT.getPrecision()+2));
			a.setV(e);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> E() {
		return E_;
	}
	
	private final Procedure1<HighPrecisionMember> GAMMA_ =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			BigDecimal gamma = new BigDecimal(GAMMA_STR.substring(0, CONTEXT.getPrecision()+2));
			a.setV(gamma);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> GAMMA() {
		return GAMMA_;
	}

	private final Procedure1<HighPrecisionMember> PHI_ =
			new Procedure1<HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a) {
			BigDecimal phi = new BigDecimal(PHI_STR.substring(0, CONTEXT.getPrecision()+2));
			a.setV(phi);
		}
	};
	
	@Override
	public Procedure1<HighPrecisionMember> PHI() {
		return PHI_;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> MIN =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			Min.compute(G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> min() {
		return MIN;
	}

	private final Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> MAX =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			Max.compute(G.HP, a, b, c);
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember,HighPrecisionMember,HighPrecisionMember> max() {
		return MAX;
	}

	private final Procedure2<HighPrecisionMember,HighPrecisionMember> REAL =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(a.v());
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> real() {
		return REAL;
	}
	
	private final Procedure2<HighPrecisionMember,HighPrecisionMember> UNREAL =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimal.ZERO);
		}
	};
	
	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> unreal() {
		return UNREAL;
	}

	@Override
	public Procedure2<HighPrecisionMember,HighPrecisionMember> conjugate() {
		return ASSIGN;
	}

	private final Function1<Boolean, HighPrecisionMember> ISZERO =
			new Function1<Boolean, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a) {
			return a.v().signum() == 0;
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionMember> isZero() {
		return ISZERO;
	}

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> scale() {
		return MUL;
	}

	private final Procedure3<RationalMember, HighPrecisionMember, HighPrecisionMember> SBR =
			new Procedure3<RationalMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(RationalMember a, HighPrecisionMember b, HighPrecisionMember c) {
			BigDecimal tmp = b.v();
			tmp = tmp.multiply(new BigDecimal(a.n()));
			tmp = tmp.divide(new BigDecimal(a.d()),CONTEXT);
			c.setV(tmp);
		}
	};

	@Override
	public Procedure3<RationalMember, HighPrecisionMember, HighPrecisionMember> scaleByRational() {
		return SBR;
	}

	private final Procedure3<Double, HighPrecisionMember, HighPrecisionMember> SBD =
			new Procedure3<Double, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(Double a, HighPrecisionMember b, HighPrecisionMember c) {
			BigDecimal d = BigDecimal.valueOf(a);
			BigDecimal tmp = b.v();
			tmp = tmp.multiply(d);
			c.setV(tmp);
		}
	};

	@Override
	public Procedure3<Double, HighPrecisionMember, HighPrecisionMember> scaleByDouble() {
		return SBD;
	}

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> scaleByHighPrec() {
		return MUL;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> ASINH =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.asinh(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> asinh() {
		return ASINH;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> ACOSH =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.acosh(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> acosh() {
		return ACOSH;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> ATANH =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.atanh(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> atanh() {
		return ATANH;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> ASIN =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.asin(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> asin() {
		return ASIN;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> ACOS =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.acos(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> acos() {
		return ACOS;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> ATAN =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.atan(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> atan() {
		return ATAN;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> SINH =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.sinh(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> sinh() {
		return SINH;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> COSH =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.cosh(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> cosh() {
		return COSH;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> SINHANDCOSH =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			sinh().call(a, b);
			cosh().call(a, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> sinhAndCosh() {
		return SINHANDCOSH;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> TANH =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.tanh(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> tanh() {
		return TANH;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> SIN =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.sin(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> sin() {
		return SIN;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> COS =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.cos(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> cos() {
		return COS;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> TAN =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.tan(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> tan() {
		return TAN;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> SINANDCOS =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			sin().call(a, b);
			cos().call(a, c);
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> sinAndCos() {
		return SINANDCOS;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> SINCH =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			if (a.v().signum() == 0)
				b.setV(BigDecimal.ONE);
			else
				b.setV(BigDecimalMath.sinh(a.v(), CONTEXT).divide(a.v(), HighPrecisionAlgebra.getContext()));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> sinch() {
		return SINCH;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> SINCHPI =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			if (a.v().signum() == 0)
				b.setV(BigDecimal.ONE);
			else {
				BigDecimal term = a.v().multiply(BigDecimalMath.e(CONTEXT));
				b.setV(BigDecimalMath.sinh(term, CONTEXT).divide(term, HighPrecisionAlgebra.getContext()));
			}
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> sinchpi() {
		return SINCHPI;
	}
	
	private final Procedure2<HighPrecisionMember, HighPrecisionMember> SINC =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			if (a.v().signum() == 0)
				b.setV(BigDecimal.ONE);
			else
				b.setV(BigDecimalMath.sin(a.v(), CONTEXT).divide(a.v(), HighPrecisionAlgebra.getContext()));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> sinc() {
		return SINC;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> SINCPI =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			if (a.v().signum() == 0)
				b.setV(BigDecimal.ONE);
			else {
				BigDecimal term = a.v().multiply(BigDecimalMath.e(CONTEXT));
				b.setV(BigDecimalMath.sin(term, CONTEXT).divide(term, HighPrecisionAlgebra.getContext()));
			}
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> sincpi() {
		return SINCPI;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> POW =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV(BigDecimalMath.pow(a.v(), b.v(), CONTEXT));
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> pow() {
		return POW;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> SQRT =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.sqrt(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> sqrt() {
		return SQRT;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> CBRT =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.root(a.v(), THREE, CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> cbrt() {
		return CBRT;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> SC =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b, HighPrecisionMember c) {
			c.setV(a.v().multiply(b.v()));
		}
	};

	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> scaleComponents() {
		return SC;
	}

	private final Function3<Boolean, HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> WITHIN =
			new Function3<Boolean, HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		
		@Override
		public Boolean call(HighPrecisionMember tol, HighPrecisionMember a, HighPrecisionMember b) {
			return NumberWithin.compute(G.HP, tol, a, b);
		}
	};

	@Override
	public Function3<Boolean, HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> within() {
		return WITHIN;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> EXP =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.exp(a.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> exp() {
		return EXP;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> LOG =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(BigDecimalMath.log(b.v(), CONTEXT));
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> log() {
		return LOG;
	}

	private final Procedure3<java.lang.Integer, HighPrecisionMember, HighPrecisionMember> STWO =
			new Procedure3<java.lang.Integer, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, HighPrecisionMember a, HighPrecisionMember b) {
			ScaleHelper.compute(G.HP, G.HP, new HighPrecisionMember(BigDecimal.valueOf(2)), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, HighPrecisionMember, HighPrecisionMember> scaleByTwo() {
		return STWO;
	}

	private final Procedure3<java.lang.Integer, HighPrecisionMember, HighPrecisionMember> SHALF =
			new Procedure3<java.lang.Integer, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(java.lang.Integer numTimes, HighPrecisionMember a, HighPrecisionMember b) {
			ScaleHelper.compute(G.HP, G.HP, new HighPrecisionMember(BigDecimal.valueOf(0.5)), numTimes, a, b);
		}
	};
	
	@Override
	public Procedure3<java.lang.Integer, HighPrecisionMember, HighPrecisionMember> scaleByOneHalf() {
		return SHALF;
	}

	private final Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> CSGN =
			new Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember magnitude, HighPrecisionMember sign, HighPrecisionMember a) {
			a.setV(magnitude.v().abs().multiply(BigDecimal.valueOf(sign.v().signum())));
		}
	};
	
	@Override
	public Procedure3<HighPrecisionMember, HighPrecisionMember, HighPrecisionMember> copySign() {
		return CSGN;
	}

	private final Function1<Integer, HighPrecisionMember> GETEXP =
			new Function1<Integer, HighPrecisionMember>()
	{
		@Override
		public Integer call(HighPrecisionMember a) {
			return a.v().scale();
		}
	};

	@Override
	public Function1<Integer, HighPrecisionMember> getExponent() {
		return GETEXP;
	}

	private final Procedure3<Integer, HighPrecisionMember, HighPrecisionMember> SCALB =
			new Procedure3<Integer, HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(Integer scaleFactor, HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(a.v().multiply(BigDecimal.valueOf(2).pow(scaleFactor), CONTEXT));
		}
	};

	@Override
	public Procedure3<Integer, HighPrecisionMember, HighPrecisionMember> scalb() {
		return SCALB;
	}

	private final Procedure2<HighPrecisionMember, HighPrecisionMember> ULP =
			new Procedure2<HighPrecisionMember, HighPrecisionMember>()
	{
		@Override
		public void call(HighPrecisionMember a, HighPrecisionMember b) {
			b.setV(a.v().ulp());
		}
	};

	@Override
	public Procedure2<HighPrecisionMember, HighPrecisionMember> ulp() {
		return ULP;
	}

	private final Function1<Boolean, HighPrecisionMember> ISUNITY =
			new Function1<Boolean, HighPrecisionMember>()
	{
		@Override
		public Boolean call(HighPrecisionMember a) {
			return a.v().equals(BigDecimal.ONE);
		}
	};

	@Override
	public Function1<Boolean, HighPrecisionMember> isUnity() {
		return ISUNITY;
	}

}