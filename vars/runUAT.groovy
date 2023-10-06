void BuildCookAndRunUAT(Map args = [:]) {
    String buildCommandString = ''

    // validate the path of the runUAT batch (optional)
    String runUATPathArgument = args.getOrDefault('runUATPath', 'Engine/Build/BatchFiles/RunUAT.bat')
    if (!runUATPathArgument?.trim()) {
        currentBuild.result = 'FAILURE'
        error('BuildCookAndRunUAT exited with an error: runUATPath cannot be empty!')
        return
    }
    if (!runUATPathArgument.contains('RunUAT.bat')) {
        currentBuild.result = 'FAILURE'
        error('BuildCookAndRunUAT exited with an error: runUATPath have to contain \"RunUAT.bat\"!')
        return
    }

    buildCommandString += "${runUATPathArgument} BuildCookRun"

    // validate project path (required)
    String project = args.getOrDefault('project', null)
    if (!project?.trim()) {
        currentBuild.result = 'FAILURE'
        error('BuildCookAndRunUAT exited with an error: project argument is required but found empty!')
        return
    }

    buildCommandString += " -project=\"${project}\""

    //target validation (required)
    String targetArgument = args.getOrDefault('target', null)
    if (!targetArgument?.trim()) {
        currentBuild.result = 'FAILURE'
        error('BuildCookAndRunUAT exited with an error: target argument is required but found empty!')
        return
    }

    // add some fix parameters, which are not parametized atm
    buildCommandString += ' -build -cook -compile -iterate -stage -package -pak'

    // platform (optional)
    String platformArgument = args.getOrDefault('platform', 'Win64')
    buildCommandString += " -platform=${platformArgument}"

    //clientconfig (optional)
    String clientConfigArgument = args.getOrDefault('clientconfig', 'Development')
    buildCommandString += " -clientconfig=${clientConfigArgument}"

    //clientconfig (optional)
    String archiveDirectoryArgument = args.getOrDefault('archivedirectory', "${WORKSPACE}\\Build")
    buildCommandString += " -archive -archivedirectory=\"${archiveDirectoryArgument}\""

    // execute the batch with all arguments
    echo "Run command ${buildCommandString}"
    bat "${buildCommandString}"
    return true
}

void BuildCookAndRunUAT(String projectPath) {
    Map argumentMap = [
        'project' : projectPath
    ]
    runUAT.BuildCookAndRunUAT(argumentMap)
}
