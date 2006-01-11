package net.sf.eclipsefp.haskell.core.jparser.test;

import antlr.CommonToken;
import antlr.Token;
import antlr.TokenStream;
import antlr.TokenStreamException;
import net.sf.eclipsefp.haskell.core.jparser.HaskellLexerTokenTypes;
import net.sf.eclipsefp.haskell.core.jparser.QualifiedIdentifierFilter;

public class QualifiedIdentifierFilterTest extends TokenStreamTestCase implements HaskellLexerTokenTypes {
	
	private TokenStream fFilter;

	public void testQualifiedVariable() throws TokenStreamException {
		setInput(new CommonToken(CONSTRUCTOR_ID, "MyModule"),
				 new CommonToken(VARSYM, "."),
				 new CommonToken(VARIABLE_ID, "aFunction"));
		assertToken(QVARID, "MyModule.aFunction", fFilter.nextToken());
	}
	
	public void testQualifiedConstructor() throws TokenStreamException {
		setInput(new CommonToken(CONSTRUCTOR_ID, "MyModule"),
				 new CommonToken(VARSYM, "."),
				 new CommonToken(CONSTRUCTOR_ID, "MyCon"));
		assertToken(QCONID, "MyModule.MyCon", fFilter.nextToken());
	}
	
	public void testDoubleQualifiedConstructor() throws TokenStreamException {
		setInput(new CommonToken(CONSTRUCTOR_ID, "MyModule"),
				new CommonToken(VARSYM, "."),
				new CommonToken(CONSTRUCTOR_ID, "MySubModule"),
				new CommonToken(VARSYM, "."),
				new CommonToken(CONSTRUCTOR_ID, "MyCon"));
		assertToken(QCONID, "MyModule.MySubModule.MyCon", fFilter.nextToken());
	}
	
	public void testInvalidStream() {
		setInput(new CommonToken(CONSTRUCTOR_ID, "MyModule"),
				 new CommonToken(VARSYM, "."),
				 new CommonToken(MODULE, "module"));
		
		try {
			fFilter.nextToken();
			fail("Should reject invalid underlying stream");
		} catch (TokenStreamException e) {
			assertTrue(true);
		}
	}
	
	public void testQualifiedVarsym() throws TokenStreamException {
		setInput(new CommonToken(CONSTRUCTOR_ID, "MyModule"),
				 new CommonToken(VARSYM, "."),
				 new CommonToken(VARSYM, "$$"));
		assertToken(QVARSYM, "MyModule.$$", fFilter.nextToken());
	}

	public void testDoNotMessWithUnqualifiedConstructors() throws TokenStreamException {
		setInput(new CommonToken(CONSTRUCTOR_ID, "Tree"),
				 new CommonToken(VARIABLE_ID, "aVar"));
		assertToken(CONSTRUCTOR_ID, "Tree", fFilter.nextToken());
		assertToken(VARIABLE_ID, "aVar", fFilter.nextToken());
	}
	
	private void setInput(final Token... tokens) {
		TokenStream input = new TestTokenStream(tokens);
		fFilter = new QualifiedIdentifierFilter(input);
	}
	
	private static class TestTokenStream implements TokenStream {

		private Token[] fTokens;
		private int fCurrentToken = 0;
		private final Token EOF = new CommonToken(HaskellLexerTokenTypes.EOF, "<<eof>>");
		
		public TestTokenStream(Token... tokens) {
			fTokens = tokens;
		}
		
		public Token nextToken() throws TokenStreamException {
			if (fCurrentToken < fTokens.length) {
				return fTokens[fCurrentToken++];
			} else {
				return EOF;
			}
		}
		
	}

}
