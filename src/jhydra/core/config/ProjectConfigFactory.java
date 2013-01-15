package jhydra.core.config;

import jhydra.core.config.exceptions.InvalidProjectNameException;
import jhydra.core.exceptions.FatalException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: jantic
 * Date: 1/14/13
 */
public class ProjectConfigFactory {
    private final IProgramConfig programConfig;

    public ProjectConfigFactory(IProgramConfig programConfig){
        this.programConfig = programConfig;
    }

    public IProjectConfig getProjectConfigByName(String name) throws FatalException {
        final List<IProjectConfig> projectConfigs = getAllProjectConfigs();

        for(IProjectConfig projectConfig : projectConfigs){
            final String projectName = projectConfig.getProjectName();
            if(projectName.equalsIgnoreCase(name)){
                return projectConfig;
            }
        }

        throw new InvalidProjectNameException(name);
    }

    public List<IProjectConfig> getAllProjectConfigs() throws FatalException {
        final File projectsDirectory = new File(programConfig.getProjectsDirectory());
        return getAllProjectConfigs(projectsDirectory);
    }

    //recursively searches until a hit is made.
    public List<IProjectConfig> getAllProjectConfigs(File directory) throws FatalException {
        final File file = FileUtils.getFile(directory, "jhydra.project");
        final List<IProjectConfig> projectConfigs = new ArrayList<>();

        if(file.exists()){
            final IProjectConfig projectConfig = new ProjectConfig(programConfig, file.toURI());
            projectConfigs.add(projectConfig);
        }
        else{
            final FileFilter directoryFilter = getDirectoryFilter();
            final File[] subDirectories = directory.listFiles(directoryFilter);
            if(subDirectories != null){
                for(File subDirectory : subDirectories){
                    projectConfigs.addAll(getAllProjectConfigs(subDirectory));
                }
            }
        }

        return projectConfigs;
    }

    private IOFileFilter getDirectoryFilter(){
        return new IOFileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() && !isTempDirectory(file) && !isHiddenDirectory(file);
            }

            @Override
            public boolean accept(File file, String s) {
                return file.isDirectory() && !isTempDirectory(file) && !isHiddenDirectory(file);
            }
        };
    }

    private Boolean isTempDirectory(File file){
        return FilenameUtils.getName(file.getName()).equalsIgnoreCase("temp");
    }

    private Boolean isHiddenDirectory(File file){
        return file.isHidden();
    }
}
