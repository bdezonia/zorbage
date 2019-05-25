/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (C) 2016-2019 Barry DeZonia
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package nom.bdezonia.zorbage.type.data.floatunlim.real;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import ch.obermuhlner.math.big.BigDecimalMath;
import nom.bdezonia.zorbage.algebras.G;
import nom.bdezonia.zorbage.algorithm.Max;
import nom.bdezonia.zorbage.algorithm.Min;
import nom.bdezonia.zorbage.function.Function1;
import nom.bdezonia.zorbage.function.Function2;
import nom.bdezonia.zorbage.procedure.Procedure1;
import nom.bdezonia.zorbage.procedure.Procedure2;
import nom.bdezonia.zorbage.procedure.Procedure3;
import nom.bdezonia.zorbage.type.algebra.Conjugate;
import nom.bdezonia.zorbage.type.algebra.Constants;
import nom.bdezonia.zorbage.type.algebra.Hyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseHyperbolic;
import nom.bdezonia.zorbage.type.algebra.InverseTrigonometric;
import nom.bdezonia.zorbage.type.algebra.Norm;
import nom.bdezonia.zorbage.type.algebra.OrderedField;
import nom.bdezonia.zorbage.type.algebra.Power;
import nom.bdezonia.zorbage.type.algebra.RealUnreal;
import nom.bdezonia.zorbage.type.algebra.Roots;
import nom.bdezonia.zorbage.type.algebra.Scale;
import nom.bdezonia.zorbage.type.algebra.ScaleByHighPrec;
import nom.bdezonia.zorbage.type.algebra.ScaleByRational;
import nom.bdezonia.zorbage.type.algebra.Trigonometric;
import nom.bdezonia.zorbage.type.data.rational.RationalMember;

/**
 * 
 * @author Barry DeZonia
 *
 */
public class HighPrecisionAlgebra
  implements
    OrderedField<HighPrecisionAlgebra,HighPrecisionMember>,
    Norm<HighPrecisionMember,HighPrecisionMember>,
    Constants<HighPrecisionMember>,
    RealUnreal<HighPrecisionMember,HighPrecisionMember>,
    Conjugate<HighPrecisionMember>,
    Scale<HighPrecisionMember,HighPrecisionMember>,
    ScaleByHighPrec<HighPrecisionMember>,
    ScaleByRational<HighPrecisionMember>,
    Trigonometric<HighPrecisionMember>,
    Hyperbolic<HighPrecisionMember>,
    InverseTrigonometric<HighPrecisionMember>,
    InverseHyperbolic<HighPrecisionMember>,
    Roots<HighPrecisionMember>,
    Power<HighPrecisionMember>
{
	private static MathContext CONTEXT = new MathContext(35, RoundingMode.HALF_EVEN);
	private static final BigDecimal THREE = BigDecimal.valueOf(3);
	
	public static void setPrecision(int decimalPlaces) {
		if (decimalPlaces < 1)
			throw new IllegalArgumentException("number of decimal places must be > 0");
		int maxPlaces = Math.min(Math.min(PI_STR.length()-2, E_STR.length()-2), Math.min(GAMMA_STR.length()-2, PHI_STR.length()-2));
		if (decimalPlaces > maxPlaces)
			throw new IllegalArgumentException("precision too high: beyond max accuracy of "+maxPlaces+" decimal places");
		CONTEXT = new MathContext(decimalPlaces, RoundingMode.HALF_EVEN);
	}

	// Source: http://turnbull.mcs.st-and.ac.uk/history/HistTopics/2000_places.html

	private static final String PI_STR    = "3.14159265358979323846264338327950288419716939937510582097494459230781640628620899862803482534211706798214808651328230664709384460955058223172535940812848111745028410270193852110555964462294895493038196442881097566593344612847564823378678316527120190914564856692346034861045432664821339360726024914127372458700660631558817488152092096282925409171536436789259036001133053054882046652138414695194151160943305727036575959195309218611738193261179310511854807446237996274956735188575272489122793818301194912983367336244065664308602139494639522473719070217986094370277053921717629317675238467481846766940513200056812714526356082778577134275778960917363717872146844090122495343014654958537105079227968925892354201995611212902196086403441815981362977477130996051870721134999999837297804995105973173281609631859502445945534690830264252230825334468503526193118817101000313783875288658753320838142061717766914730359825349042875546873115956286388235378759375195778185778053217122680661300192787661119590921642019893809525720106548586327886593615338182796823030195203530185296899577362259941389124972177528347913151557485724245415069595082953311686172785588907509838175463746493931925506040092770167113900984882401285836160356370766010471018194295559619894676783744944825537977472684710404753464620804668425906949129331367702898915210475216205696602405803815019351125338243003558764024749647326391419927260426992279678235478163600934172164121992458631503028618297455570674983850549458858692699569092721079750930295532116534498720275596023648066549911988183479775356636980742654252786255181841757467289097777279380008164706001614524919217321721477235014144197356854816136115735255213347574184946843852332390739414333454776241686251898356948556209921922218427255025425688767179049460165346680498862723279178608578438382796797668145410095388378636095068006422512520511739298489608412848862694560424196528502221066118630674427862203919494504712371378696095636437191728746776465757396241389086583264599581339047802759010";

	// Source: http://www-history.mcs.st-and.ac.uk/HistTopics/e_10000.html
	
	private static final String E_STR     = "2.71828182845904523536028747135266249775724709369995957496696762772407663035354759457138217852516642742746639193200305992181741359662904357290033429526059563073813232862794349076323382988075319525101901157383418793070215408914993488416750924476146066808226480016847741185374234544243710753907774499206955170276183860626133138458300075204493382656029760673711320070932870912744374704723069697720931014169283681902551510865746377211125238978442505695369677078544996996794686445490598793163688923009879312773617821542499922957635148220826989519366803318252886939849646510582093923982948879332036250944311730123819706841614039701983767932068328237646480429531180232878250981945581530175671736133206981125099618188159304169035159888851934580727386673858942287922849989208680582574927961048419844436346324496848756023362482704197862320900216099023530436994184914631409343173814364054625315209618369088870701676839642437814059271456354906130310720851038375051011574770417189861068739696552126715468895703503540212340784981933432106817012100562788023519303322474501585390473041995777709350366041699732972508868769664035557071622684471625607988265178713419512466520103059212366771943252786753985589448969709640975459185695638023637016211204774272283648961342251644507818244235294863637214174023889344124796357437026375529444833799801612549227850925778256209262264832627793338656648162772516401910590049164499828931505660472580277863186415519565324425869829469593080191529872117255634754639644791014590409058629849679128740687050489585867174798546677575732056812884592054133405392200011378630094556068816674001698420558040336379537645203040243225661352783695117788386387443966253224985065499588623428189970773327617178392803494650143455889707194258639877275471096295374152111513683506275260232648472870392076431005958411661205452970302364725492966693811513732275364509888903136020572481765851180630364428123149655070475102544650117272115551948668508003685322818315219600373562527944951582841882947876108526398139";

	// source: wolframalpha
	
	private static final String GAMMA_STR = "0.57721566490153286060651209008240243104215933593992359880576723488486772677766467093694706329174674951463144724980708248096050401448654283622417399764492353625350033374293733773767394279259525824709491600873520394816567085323315177661152862119950150798479374508570574002992135478614669402960432542151905877553526733139925401296742051375413954911168510280798423487758720503843109399736137255306088933126760017247953783675927135157722610273492913940798430103417771778088154957066107501016191663340152278935867965497252036212879226555953669628176388792726801324310104765059637039473949576389065729679296010090151251959509222435014093498712282479497471956469763185066761290638110518241974448678363808617494551698927923018773910729457815543160050021828440960537724342032854783670151773943987003023703395183286900015581939880427074115422278197165230110735658339673487176504919418123000406546931429992977795693031005030863034185698032310836916400258929708909854868257773642882539549258736295961332985747393023734388470703702844129201664178502487333790805627549984345907616431671031467107223700218107450444186647591348036690255324586254422253451813879124345735013612977822782881489459098638460062931694718871495875254923664935204732436410972682761608775950880951262084045444779922991572482925162512784276596570832146102982146179519579590959227042089896279712553632179488737642106606070659825619901028807561251991375116782176436190570584407835735015800560774579342131449885007864151716151945657061704324507500816870523078909370461430668481791649684254915049672431218378387535648949508684541023406016225085155838672349441878804409407701068837951113078720234263952269209716088569083825113787128368204911789259447848619911852939102930990592552669172744689204438697111471745715745732039352091223160850868275588901094516811810168749754709693666712102063048271658950493273148608749402070067425909182487596213738423114426531350292303175172257221628324883811245895743862398703757662855130331439299954018531341415862127";
	
	// source: wolframalpha
	
	private static final String PHI_STR   = "1.61803398874989484820458683436563811772030917980576286213544862270526046281890244970720720418939113748475408807538689175212663386222353693179318006076672635443338908659593958290563832266131992829026788067520876689250171169620703222104321626954862629631361443814975870122034080588795445474924618569536486444924104432077134494704956584678850987433944221254487706647809158846074998871240076521705751797883416625624940758906970400028121042762177111777805315317141011704666599146697987317613560067087480710131795236894275219484353056783002287856997829778347845878228911097625003026961561700250464338243776486102838312683303724292675263116533924731671112115881863851331620384005222165791286675294654906811317159934323597349498509040947621322298101726107059611645629909816290555208524790352406020172799747175342777592778625619432082750513121815628551222480939471234145170223735805772786160086883829523045926478780178899219902707769038953219681986151437803149974110692608867429622675756052317277752035361393621076738937645560606059216589466759551900400555908950229530942312482355212212415444006470340565734797663972394949946584578873039623090375033993856210242369025138680414577995698122445747178034173126453220416397232134044449487302315417676893752103068737880344170093954409627955898678723209512426893557309704509595684401755519881921802064052905518934947592600734852282101088194644544222318891319294689622002301443770269923007803085261180754519288770502109684249362713592518760777884665836150238913493333122310533923213624319263728910670503399282265263556209029798642472759772565508615487543574826471814145127000602389016207773224499435308899909501680328112194320481964387675863314798571911397815397807476150772211750826945863932045652098969855567814106968372884058746103378105444390943683583581381131168993855576975484149144534150912954070050194775486163075422641729394680367319805861833918328599130396072014455950449779212076124785645916160837059498786006970189409886400764436170933417270919143365013716";
	
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
			a.setV( BigDecimal.ZERO );
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

	private Function2<java.lang.Integer,HighPrecisionMember,HighPrecisionMember> CMP =
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

	private Function1<java.lang.Integer,HighPrecisionMember> SIG =
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
			abs().call(a,b);
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
			Min.compute(G.FLOAT_UNLIM, a, b, c);
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
			Max.compute(G.FLOAT_UNLIM, a, b, c);
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
			tmp = tmp.multiply(new BigDecimal(a.n()),CONTEXT);
			tmp = tmp.divide(new BigDecimal(a.d()),CONTEXT);
			c.setV(tmp);
		}
	};

	@Override
	public Procedure3<RationalMember, HighPrecisionMember, HighPrecisionMember> scaleByRational() {
		return SBR;
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
				b.setV(BigDecimalMath.sinh(a.v(), CONTEXT).divide(a.v()));
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
				b.setV(BigDecimalMath.sinh(term, CONTEXT).divide(term));
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
				b.setV(BigDecimalMath.sin(a.v(), CONTEXT).divide(a.v()));
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
				b.setV(BigDecimalMath.sin(term, CONTEXT).divide(term));
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

}