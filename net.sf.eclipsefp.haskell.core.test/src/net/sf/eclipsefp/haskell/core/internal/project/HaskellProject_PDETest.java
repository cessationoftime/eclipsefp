// Copyright (c) 2003-2005 by Leif Frenzel - see http://leiffrenzel.de
package net.sf.eclipsefp.haskell.core.internal.project;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import net.sf.eclipsefp.haskell.core.project.HaskellProjectManager;
import net.sf.eclipsefp.haskell.core.test.TestCaseWithProject;
import net.sf.eclipsefp.haskell.core.util.ResourceUtil;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;


public class HaskellProject_PDETest extends TestCaseWithProject {

  // interface methods of TestCase
  ////////////////////////////////

  @Override
  protected void tearDown() throws Exception {
    HaskellProjectManager.clear();
    super.tearDown();
  }


  // test cases
  /////////////

  public void testSingleSourcePath() {
    HaskellProject hp = ( HaskellProject )HaskellProjectManager.get( project );
    assertEquals( 1, hp.getSourcePaths().size() );
    assertEquals( project.getFolder( "src" ), hp.getSourceFolder() );

    assertTrue( ResourceUtil.isSourceFolder( project.getFolder( "src" ) ) );
    assertFalse( ResourceUtil.isSourceFolder( project.getFolder( "crs" ) ) );
    assertFalse( ResourceUtil.isSourceFolder( project.getFolder( "src/bla" ) ) );
  }

  public void testMultipleSourcePaths() {
    HaskellProject hp = ( HaskellProject )HaskellProjectManager.get( project );
    hp.addSourcePath( new Path("test") );
    assertEquals( 2, hp.getSourcePaths().size() );
    // TODO lf we should really get two here
//    assertEquals( project.getFolder( "src" ), hp.getSourceFolder() );

    assertTrue( ResourceUtil.isSourceFolder( project.getFolder( "src" ) ) );
    assertTrue( ResourceUtil.isSourceFolder( project.getFolder( "test" ) ) );
    assertFalse( ResourceUtil.isSourceFolder( project.getFolder( "crs" ) ) );
  }

  public void testTargetExecutable_single() throws CoreException {
    HaskellProject hp = ( HaskellProject )HaskellProjectManager.get( project );
    // atm one target exe is added automatically during project creation
    assertEquals( 1, hp.getTargets().size() );

    IPath path = new Path( "bla" );
    InputStream is = new ByteArrayInputStream( new byte[ 0 ] );
    IFolder out = project.getFolder( "out" );
    out.getFile( ResourceUtil.executableName( path ) ).create( is, true, null );
    hp.addTarget( new ExecutableBuildTarget( path, "Main.main" ) );
    assertEquals( 2, hp.getTargets().size() );

    assertTrue( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "out/bla" ) ) ) );
    assertFalse( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "bla" ) ) ) );
    assertFalse( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "out/blubb" ) ) ) );
  }

  public void testTargetExecutable_multiple() throws CoreException {
    HaskellProject hp = ( HaskellProject )HaskellProjectManager.get( project );
    // atm one target exe is added automatically during project creation
    assertEquals( 1, hp.getTargets().size() );

    IPath path = new Path( "bli" );
    IPath path2 = new Path( "sub/bla" );
    InputStream is = new ByteArrayInputStream( new byte[ 0 ] );
    IFolder out = project.getFolder( "out" );
    out.getFolder( "sub" ).create( true, true, null );
    out.getFile( ResourceUtil.executableName( path ) ).create( is, true, null );
    out.getFile( ResourceUtil.executableName( path2 ) ).create( is, true, null );
    hp.addTarget( new ExecutableBuildTarget( path, "Main.main" ) );
    hp.addTarget( new ExecutableBuildTarget( path2, "Main.main" ) );
    assertEquals( 3, hp.getTargets().size() );

    assertTrue( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "out/bli" ) ) ) );
    assertTrue( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "out/sub/bla" ) ) ) );
    assertFalse( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "bli" ) ) ) );
    assertFalse( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "out/sub/bli" ) ) ) );
    assertFalse( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "bla" ) ) ) );
    assertFalse( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "out/bla" ) ) ) );
    assertFalse( ResourceUtil.isProjectExecutable( project.getFile( ResourceUtil.executableName( "out/blubb" ) ) ) );
  }
}
