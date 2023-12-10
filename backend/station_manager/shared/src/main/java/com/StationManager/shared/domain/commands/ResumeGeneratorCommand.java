package com.StationManager.shared.domain.commands;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeGeneratorCommand extends Command {
    @JsonCreator
    public ResumeGeneratorCommand() { }
}
