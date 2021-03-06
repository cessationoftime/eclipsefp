package net.sf.eclipsefp.haskell.core.partitioned.happy;

import net.sf.eclipsefp.haskell.core.HaskellCorePlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;

/**
 * Cleans resource markers from Happy.
 *
 * @author Alejandro Serrano
 */
public class CleanVisitor implements IResourceVisitor {

  @Override
  public boolean visit( final IResource resource ) throws CoreException {
    if( HappyBuilder.mustBeVisited( resource ) ) {
      // We have to clean the previous markers
      resource.deleteMarkers( HaskellCorePlugin.ID_HAPPY_MARKER, true,
          IResource.DEPTH_ZERO );
    }
    return true;
  }

}
